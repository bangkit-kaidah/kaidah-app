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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(this)
        val isSignedin = sharedPreference.getValueBoolien("signed", false)
        val isGuest = sharedPreference.getValueBoolien("guest", false)

        val fragOne = HomeFragment()
        val fragTwo = MemberFragment()
        val fragThree = ProfileFragment()
        val fragFour = LoginFragment()
        val fragFive = CategoryFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_fragment, fragOne, "FragmentHome")
            commit()
        }
        binding.ivHome.setImageResource(R.drawable.home_2)

        binding.fHome.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragOne, "FragmentHome")
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home_2)
            binding.ivCategory.setImageResource(R.drawable.maintenance_1)
            binding.ivFollowed.setImageResource(R.drawable.admin_1)
            binding.ivProfile.setImageResource(R.drawable.profile_1)
        }

        binding.fCategory.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragFive, "CategoryHome")
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home_1)
            binding.ivCategory.setImageResource(R.drawable.maintenance_2)
            binding.ivFollowed.setImageResource(R.drawable.admin_1)
            binding.ivProfile.setImageResource(R.drawable.profile_1)
        }

        binding.fFollowed.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragTwo, "FragmentFollowed")
                commit()
            }
            binding.ivHome.setImageResource(R.drawable.home_1)
            binding.ivCategory.setImageResource(R.drawable.maintenance_1)
            binding.ivFollowed.setImageResource(R.drawable.admin_2)
            binding.ivProfile.setImageResource(R.drawable.profile_1)
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
            binding.ivHome.setImageResource(R.drawable.home_1)
            binding.ivCategory.setImageResource(R.drawable.maintenance_1)
            binding.ivFollowed.setImageResource(R.drawable.admin_1)
            binding.ivProfile.setImageResource(R.drawable.profile_2)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity).setTitle("Keluar Aplikasi").setMessage("Apakah anda ingin menutup aplikasi?").setPositiveButton(R.string.ya) {
            dialog, whichButton -> super.onBackPressed()
            finishAffinity()
        } .setNegativeButton(R.string.tidak) {
            dialog, whichButton -> //Close
        } .show()
    }
}