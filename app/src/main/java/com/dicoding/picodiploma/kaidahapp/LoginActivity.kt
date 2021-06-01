package com.dicoding.picodiploma.kaidahapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.kaidahapp.entity.LoginResponse
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(this)
        val isSignedin = sharedPreference.getValueBoolien("signed", false)
        val isGuest = sharedPreference.getValueBoolien("guest", false)

        if (isSignedin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        if (isGuest) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val email = binding.edEmail.text
        val password = binding.edPassword.text

        var respond: String? = null
        binding.btnSignin.setOnClickListener{
            Log.d("checkParam", "email: ${email} | Password: ${password}")
            if (email.toString().trim().isEmpty()){
                binding.edEmail.error = "Field ini tidak boleh kosong"
            }
            if(password.toString().trim().isEmpty()){
                binding.edPassword.error = "Field ini tidak boleh kosong"
            }
            if (email.toString().trim().isNotEmpty() && password.toString().trim().isNotEmpty()){
                binding.btnSignin.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.tvNoUser.visibility = View.GONE
                RetrofitClient.instance.userLogin(email.toString(), password.toString()).enqueue(object: Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        respond = response.body()?.toString()
                        Log.d("Teasddd1", response.body()?.message.toString())
                        if (!respond.isNullOrEmpty()){
                            Log.d("Teasddd2", respond.toString())
                            sharedPreference.save("email", response.body()?.user!!.email)
                            sharedPreference.save("token", response.body()?.token!!)
                            sharedPreference.save("password", password.toString())
                            sharedPreference.save("signed", true)

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                            binding.btnSignin.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                            binding.tvNoUser.visibility = View.GONE
                        } else {
                            Log.d("Teasddd4", respond.toString())
                            Log.d("Teasddd3", response.body()?.message.toString())
                            binding.tvNoUser.visibility = View.VISIBLE
                            binding.btnSignin.visibility = View.VISIBLE
                            binding.progressBar.visibility = View.GONE
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

        binding.tvSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.tvSigninGuest.setOnClickListener {
            sharedPreference.save("guest", true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}