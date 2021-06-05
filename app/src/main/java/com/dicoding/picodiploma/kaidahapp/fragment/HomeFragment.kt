package com.dicoding.picodiploma.kaidahapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kaidahapp.SplashScreenActivity
import com.dicoding.picodiploma.kaidahapp.adapter.SubjectAdapter
import com.dicoding.picodiploma.kaidahapp.api.RetrofitClient
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.kaidahapp.entity.FeaturedSubjectsResponse
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment() : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var sharedPreference: SharedPreference
    private lateinit var adapter: SubjectAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTopSubject.layoutManager = GridLayoutManager(context, 2)
        adapter = SubjectAdapter { subject : FeaturedSubjectsResponse -> partItemClicked(subject) }
        binding.rvTopSubject.adapter = adapter

        inputData()

    }

    private fun inputData(){
        val dataFeaturedSubject = SplashScreenActivity.dataFeaturedSubjects
        adapter.setData(dataFeaturedSubject)
        adapter.notifyDataSetChanged()
    }

    private fun partItemClicked(subject : FeaturedSubjectsResponse) {
        Toast.makeText(context, "${subject.name} Clicked", Toast.LENGTH_SHORT).show()
    }
}