package com.dicoding.picodiploma.kaidahapp.fragment

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.location.Address
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import com.dicoding.picodiploma.kaidahapp.LoginActivity
import com.dicoding.picodiploma.kaidahapp.PremiumActivity
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentProfileBinding
import com.dicoding.picodiploma.kaidahapp.entity.LoginResponse
import com.dicoding.picodiploma.kaidahapp.entity.ProfileResponse
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import okhttp3.internal.notify
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        sharedPreference = SharedPreference(requireContext())

        val name = sharedPreference.getValueString("name")
        val email = sharedPreference.getValueString("email")
        val phone = sharedPreference.getValueString("phone")
        val address = sharedPreference.getValueString("address")
        val info = sharedPreference.getValueString("info")
        val roleId = sharedPreference.getValueInt("roleId")
        binding.tvName.text = name
        binding.tvInfo.text = info
        binding.edName.setText(name)
        binding.edEmail.setText(email)
        binding.edPhone.setText(phone)
        binding.edAddress.setText(address)
        if (roleId == 2) {
            binding.tvUserRole.text = roleId.toString()
        }

        Log.d("ProfileFound", "Nama: ${name}\nEmail: ${email}\nPhone: ${phone}\nAddress: ${address}\nInfo: ${info}\nRoleID: ${roleId}")

        binding.btnSignout.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Keluar Sebagai Pengguna").setMessage("Apakah anda ingin keluar sebagai pengguna ini?").setPositiveButton(R.string.ya) {
                dialog, whichButton ->
                sharedPreference.clearSharedPreference()
                activity?.finishAndRemoveTask()
            } .setNegativeButton(R.string.tidak) {
                dialog, whichButton -> //Close
            } .show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var openSetting = false
        binding.ivSetting.setOnClickListener {
            if (openSetting) {
                openSetting = false
                closeSettingView(binding.spaceSetting, binding.spaceProfil, binding.space)
            } else {
                openSetting = true
                openSettingView(binding.spaceSetting, binding.spaceProfil, binding.space)
            }
        }
    }

    private fun openSettingView(view: View, fade: View, parent: View) {
        view.visibility = View.VISIBLE
        fade.animate().alpha(0.0f).setDuration(100)
        val valueAnimator = ValueAnimator.ofInt(view.measuredHeight, parent.measuredHeight)
        valueAnimator.duration = 200L
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }
    private fun closeSettingView(view: View, fade: View, parent: View) {
        val valueAnimator = ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight - parent.measuredHeight)
        valueAnimator.duration = 50L
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
        fade.animate().alpha(1f).setDuration(100);
    }
}