package com.dicoding.picodiploma.kaidahapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(this)
        val isSignedin = sharedPreference.getValueBoolien("signed", false)

        if (isSignedin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val animationDrawable = binding.abc.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2000)
        animationDrawable.setExitFadeDuration(4000)
        animationDrawable.start()

        val username = binding.edUsername.text
        val password = binding.edPassword.text

        binding.btnSignin.setOnClickListener{
            Log.d("checkParam", "Username: ${username} | Password: ${password}")
            login(this, username.toString(), password.toString())
        }
    }

    private fun login(context: Context, username: String, password: String) {
        Log.d("checkParam2", "Username: ${username} | Password: ${password}")
        if (username == "coba") {
            if (password == "coba") {
                sharedPreference.save("username", username)
                sharedPreference.save("password", password)
                sharedPreference.save("signed", true)

                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Kata Sandi Tidak Dapat ditemukan", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Nama Pengguna Tidak Dapat ditemukan", Toast.LENGTH_SHORT).show()
        }
    }
}