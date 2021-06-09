package com.dicoding.picodiploma.kaidahapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.SplashScreenActivity
import com.dicoding.picodiploma.kaidahapp.adapter.RegulationAdapter
import com.dicoding.picodiploma.kaidahapp.adapter.TopSubjectAdapter
import com.dicoding.picodiploma.kaidahapp.databinding.ActivityMainBinding
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.kaidahapp.datadetail.DetailActivity
import com.dicoding.picodiploma.kaidahapp.dataregulation.PageRegulationActivity
import com.dicoding.picodiploma.kaidahapp.entity.FeaturedSubjectsResponse
import com.dicoding.picodiploma.kaidahapp.entity.RegulationParam
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference

class HomeFragment() : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var sharedPreference: SharedPreference
    private lateinit var adapterFeaturedTopSubject: TopSubjectAdapter
    private lateinit var adapterTopRegulation: RegulationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTopSubject.layoutManager = GridLayoutManager(context, 2)
        adapterFeaturedTopSubject = TopSubjectAdapter { subject : FeaturedSubjectsResponse -> partItemClickedSubject(subject) }
        binding.rvTopSubject.adapter = adapterFeaturedTopSubject

        binding.rvNewRegulation.layoutManager = LinearLayoutManager(context)
        adapterTopRegulation = RegulationAdapter { regulation : RegulationParam -> partItemClickedRegulation(regulation) }
        binding.rvNewRegulation.adapter = adapterTopRegulation

        inputData()
    }

    private fun inputData(){
        val dataFeaturedSubject = SplashScreenActivity.dataFeaturedSubjects
        adapterFeaturedTopSubject.setData(dataFeaturedSubject)
        adapterFeaturedTopSubject.notifyDataSetChanged()

        val dataTopRegulation = SplashScreenActivity.dataTopRegulation
        adapterTopRegulation.setData(dataTopRegulation)
        adapterTopRegulation.notifyDataSetChanged()
    }

    private fun partItemClickedSubject(subject : FeaturedSubjectsResponse) {
        if (subject.subject_id == -1){
            val frame = activity?.findViewById<FrameLayout>(R.id.frame_fragment)
            val homeImage = requireActivity().findViewById<ImageView>(R.id.ivHome)
            val subjectImage = requireActivity().findViewById<ImageView>(R.id.ivCategory)
            val fragment = SubjectFragment()
            requireActivity().supportFragmentManager.beginTransaction().apply {
                frame?.let { replace(it.id, fragment) }
                commit()
            }
            homeImage.setImageResource(R.drawable.home)
            subjectImage.setImageResource(R.drawable.subject_active)
        }
        if (subject.subject_id != -1) {
            val intent = Intent(requireActivity(), PageRegulationActivity::class.java)
            intent.putExtra(PageRegulationActivity.EXTRA, subject.subject_id)
            startActivity(intent)
        }
    }

    private fun partItemClickedRegulation(regulation : RegulationParam) {
        if (regulation.id == -1){
            val intent = Intent(requireActivity(), PageRegulationActivity::class.java)
            intent.putExtra(PageRegulationActivity.EXTRA, regulation.id)
            startActivity(intent)
        }
        if (regulation.id != -1) {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, regulation.id)
            startActivity(intent)
        }
    }
}