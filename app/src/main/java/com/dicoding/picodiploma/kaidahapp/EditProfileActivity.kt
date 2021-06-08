package com.dicoding.picodiploma.kaidahapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityEditProfileBinding
import com.dicoding.picodiploma.kaidahapp.entity.LoginResponse
import com.dicoding.picodiploma.kaidahapp.entity.ProfileResponse
import com.dicoding.picodiploma.kaidahapp.entity.RegisterResponse
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = binding.edName.text
        val phone = binding.edPhone.text
        val address = binding.edAddress.text

        var phoneParam: String? = null
        var addressParam: String? = null

        if (!phone.toString().trim().isEmpty()) {
            phoneParam = phone.toString()
        }
        if (!address.toString().trim().isEmpty()) {
            addressParam = address.toString()
        }

        var respond: String? = null
        binding.btnEditProfile.setOnClickListener {
            if (name.toString().trim().isEmpty()) {
                binding.edName.error = "Field ini tidak boleh kosong"
            }
            if (name.toString().trim().isNotEmpty()) {
                binding.btnEditProfile.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                RetrofitClient.instance.userEditProfile(name.toString(), phoneParam, addressParam, null).enqueue(object: Callback<ProfileResponse> {
                    override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                        respond = response.body()?.toString()
                        if (!respond.isNullOrEmpty()) {
                            binding.btnEditProfile.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            Log.d("cobacoba2", respond.toString())
                            Toast.makeText(this@EditProfileActivity, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("cobacoba3", respond.toString())
                            binding.btnEditProfile.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                    override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
        binding.btnBack.setOnClickListener {
            this.finish()
        }
    }
}