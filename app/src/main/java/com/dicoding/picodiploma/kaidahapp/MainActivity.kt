package com.dicoding.picodiploma.kaidahapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.kaidahapp.fragment.FollowedFragment
import com.dicoding.picodiploma.kaidahapp.fragment.HomeFragment
import com.dicoding.picodiploma.kaidahapp.fragment.ProfileFragment
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = SharedPreference(this)

        val fragOne = HomeFragment()
        val fragTwo = FollowedFragment()
        val fragThree = ProfileFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_fragment, fragOne, "FragmentHome")
            commit()
        }

        binding.fHome.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragOne, "FragmentHome")
                commit()
            }
        }

        binding.fFollowed.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragTwo, "FragmentFollowed")
                commit()
            }
        }

        binding.fProfile.setOnClickListener {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frame_fragment, fragThree, "FragmentProfile")
                commit()
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@MainActivity).setTitle("Keluar Aplikasi").setMessage("Apakah anda ingin menutup aplikasi?").setPositiveButton(R.string.ya) {
            dialog, whichButton -> super.onBackPressed()
            finishAffinity()
        } .setNegativeButton(R.string.tidak) {
            dialog, whichButton -> //Close
        } .show()
//        val currentFragment = this@MainActivity.supportFragmentManager.findFragmentByTag("FragmentHome")
//        if(currentFragment is HomeFragment) {
//            AlertDialog.Builder(this@MainActivity).setTitle("Keluar Aplikasi").setMessage("Apakah anda ingin menutup aplikasi?").setPositiveButton(R.string.ya) {
//                dialog, whichButton -> super.onBackPressed()
//            } .setNegativeButton(R.string.tidak) {
//                dialog, whichButton -> //Close
//            } .show()
//        }
//        else{
//            super.onBackPressed()
//        }
    }
}