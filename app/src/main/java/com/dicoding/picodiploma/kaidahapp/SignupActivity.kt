package com.dicoding.picodiploma.kaidahapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivitySignupBinding
import com.dicoding.picodiploma.kaidahapp.entity.RegisterResponse
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(this)

        val name = binding.edName.text
        val phone = binding.edPhone.text
        val address = binding.edAddress.text
        val info = binding.edInfo.text
        val email = binding.edEmail.text
        val password = binding.edPassword.text
        val confirm_password = binding.edConfirmPassword.text

        var phoneParam: Int? = null
        var addressParam: String? = null
        var infoParam: String? = null

        if (!phone.toString().trim().isEmpty()) {
            phoneParam = phone.toString().toInt()
        }
        if (!address.toString().trim().isEmpty()) {
            addressParam = address.toString()
        }
        if (!info.toString().trim().isEmpty()) {
            infoParam = info.toString()
        }

        binding.btnNext.setOnClickListener {
            if (name.toString().trim().isEmpty()) {
                binding.edName.error = "Field ini tidak boleh kosong"
            }
            if (name.toString().trim().isNotEmpty()) {
                binding.userData.visibility = View.GONE
                binding.userAuth.visibility = View.VISIBLE
                binding.btnNext.visibility = View.GONE
                binding.btnBack.visibility = View.VISIBLE
                binding.btnSignup.visibility = View.VISIBLE
            }
        }
        binding.btnBack.setOnClickListener {
            binding.userData.visibility = View.VISIBLE
            binding.userAuth.visibility = View.GONE
            binding.btnNext.visibility = View.VISIBLE
            binding.btnBack.visibility = View.GONE
            binding.btnSignup.visibility = View.GONE
        }

        var respond: String? = null
        binding.btnSignup.setOnClickListener {
            if (email.toString().trim().isEmpty()) {
                binding.edEmail.error = "Field ini tidak boleh kosong"
            }
            if (password.toString().trim().isEmpty()) {
                binding.edPassword.error = "Field ini tidak boleh kosong"
            }
            if (confirm_password.toString().trim().isEmpty()) {
                binding.edConfirmPassword.error = "Field ini tidak boleh kosong"
            }
            if (password.toString() != confirm_password.toString()) {
                binding.edConfirmPassword.error = "Sandi Konfirmasi Salah"
            }
            if (email.toString().trim().isNotEmpty() && password.toString().trim().isNotEmpty() && confirm_password.toString().trim().isNotEmpty() && password.toString() == confirm_password.toString()) {
                binding.btnSignup.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.tvNoEmail.visibility = View.GONE

                RetrofitClient.instance.userRegister(name.toString(), email.toString(), password.toString(), confirm_password.toString(), phoneParam, addressParam, infoParam).enqueue(object: Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        respond = response.body()?.toString()
                        if (!respond.isNullOrEmpty()) {
                            Log.d("cobacoba2", respond.toString())
                            binding.tvNoEmail.visibility = View.GONE
                            binding.btnSignup.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            this@SignupActivity.finish()
                            Toast.makeText(this@SignupActivity, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("cobacoba3", respond.toString())
                            binding.tvNoEmail.visibility = View.VISIBLE
                            binding.btnSignup.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }
}