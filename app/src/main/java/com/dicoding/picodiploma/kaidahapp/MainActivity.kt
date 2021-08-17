package com.dicoding.picodiploma.kaidahapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.kaidahapp.fragment.*
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: SharedPreference

    companion object {
        var token = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(this)
        val isSignedin = sharedPreference.getValueBoolien("signed", false)
        val isGuest = sharedPreference.getValueBoolien("guest", false)
        token = sharedPreference.getValueString("token").toString()

        val fragOne = HomeFragment()
        val fragTwo = MemberFragment()
        val fragThree = ProfileFragment()
        val fragFour = LoginFragment()
        val fragFive = SubjectFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_fragment, fragOne, "FragmentHome")
            commit()
        }
        binding.ivHome.setImageResource(R.drawable.home_active)

        binding.fHome.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragOne, "FragmentHome")
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home_active)
            binding.ivCategory.setImageResource(R.drawable.subject)
            binding.ivFollowed.setImageResource(R.drawable.source)
            binding.ivProfile.setImageResource(R.drawable.profile)
        }

        binding.fCategory.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragFive, "SubjectHome")
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home)
            binding.ivCategory.setImageResource(R.drawable.subject_active)
            binding.ivFollowed.setImageResource(R.drawable.source)
            binding.ivProfile.setImageResource(R.drawable.profile)
        }

        binding.fFollowed.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragTwo, "FragmentFollowed")
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home)
            binding.ivCategory.setImageResource(R.drawable.subject)
            binding.ivFollowed.setImageResource(R.drawable.source_active)
            binding.ivProfile.setImageResource(R.drawable.profile)
        }

        binding.fProfile.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                if (isGuest) {
                    replace(R.id.frame_fragment, fragFour, "FragmentLogin")
                }
                if (isSignedin){
                    replace(R.id.frame_fragment, fragThree, "FragmentProfile")
                }
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home)
            binding.ivCategory.setImageResource(R.drawable.subject)
            binding.ivFollowed.setImageResource(R.drawable.source)
            binding.ivProfile.setImageResource(R.drawable.profile_active)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity).setTitle("Keluar Aplikasi").setMessage("Apakah anda ingin menutup aplikasi?").setPositiveButton(R.string.ya) {
                _, _ -> super.onBackPressed()
            finishAffinity()
        } .setNegativeButton(R.string.tidak) {
                _, _ -> //Close
        } .show()
    }

}