package com.dicoding.picodiploma.kaidahapp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentProfileBinding
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference

class ProfileFragment : Fragment() {

    private lateinit var _binding: FragmentProfileBinding
    private val binding get() = _binding
    private lateinit var sharedPreference: SharedPreference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreference = SharedPreference(requireContext())

        binding.btnSignout.setOnClickListener {
            AlertDialog.Builder(requireContext()).setTitle("Keluar Sebagai Pengguna").setMessage("Apakah anda ingin keluar sebagai pengguna ini?").setPositiveButton(R.string.ya) {
                    dialog, whichButton ->
                sharedPreference.clearSharedPreference()
                activity?.finishAndRemoveTask()
            } .setNegativeButton(R.string.tidak) {
                    dialog, whichButton -> //Close
            } .show()
        }
    }
}