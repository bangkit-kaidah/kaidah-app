package com.dicoding.picodiploma.kaidahapp

import android.content.Intent
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

        sharedPreference = SharedPreference(this)

        val nameShared = sharedPreference.getValueString("name")
        val phoneShared = sharedPreference.getValueString("phone")
        val addressShared = sharedPreference.getValueString("address")


        val name = binding.edName
        val phone = binding.edPhone
        val address = binding.edAddress

        name.setText(nameShared)
        phone.setText(phoneShared)
        address.setText(addressShared)

        var phoneParam: String? = null
        var addressParam: String? = null

        var respond: String? = null
        binding.btnEditProfile.setOnClickListener {
            if (phone.text.isNotEmpty()) {
                phoneParam = phone.text.toString()
            }
            if (address.text.isNotEmpty()) {
                addressParam = address.text.toString()
            }

            if (name.text.trim().isEmpty()) {
                binding.edName.error = "Field ini tidak boleh kosong"
            }
            if (name.text.trim().isNotEmpty()) {
                binding.btnEditProfile.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                RetrofitClient.instanceUser.userEditProfile(name.text.toString(), phoneParam, addressParam).enqueue(object: Callback<ProfileResponse> {
                    override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                        respond = response.body()?.toString()
                        response.errorBody()?.string()?.let { it1 -> Log.d("cobacoba1", it1) }
                        if (!respond.isNullOrEmpty()) {
                            sharedPreference.save("name", response.body()?.name)
                            sharedPreference.save("email", response.body()?.email)
                            sharedPreference.save("phone", phoneParam)
                            sharedPreference.save("address", addressParam)
                            sharedPreference.save("roleId", response.body()?.role_id!!)
                            binding.btnEditProfile.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            this@EditProfileActivity.finish()
                            val intent = Intent(this@EditProfileActivity, MainActivity::class.java)
                            startActivity(intent)
                            Log.d("cobacoba2", response.body().toString())
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