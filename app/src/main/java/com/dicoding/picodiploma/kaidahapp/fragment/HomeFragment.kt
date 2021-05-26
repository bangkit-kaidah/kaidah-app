package com.dicoding.picodiploma.kaidahapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.kaidahapp.adapter.JdihnMemberAdapter
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.kaidahapp.helper.SharedPreference
import com.dicoding.picodiploma.kaidahapp.entity.JdihnMemberParam

class HomeFragment() : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding
    private lateinit var sharedPreference: SharedPreference
    private lateinit var adapter: JdihnMemberAdapter
    var nameArray = arrayOf("JDIHN Kemenkeu", "JDIHN Kemenker", "JDIHN Hukum")
    var leaderArray = arrayOf("Anto Wahyudi Derma", "Puji Astuti", "Tri Andra Eko")
    var infoArray = arrayOf("JDHIN Kemenkeu adalah suatu kementrian yang berfokus pada bla bla bla", "JDIHN Kemenker adalah suatu kementrian yang berfokus pada bla bla bla", "JDIHN Hukum adalah suatu kementrian yang berfokus pada bla bla bla")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvJdihnMember.layoutManager = LinearLayoutManager(context)
        adapter = JdihnMemberAdapter { member : JdihnMemberParam -> partItemClicked(member) }
        binding.rvJdihnMember.adapter = adapter

        inputData()
    }

    private fun inputData(){
        val listItems = ArrayList<JdihnMemberParam>()
        for (x in 0..nameArray.size-1){
            val item = JdihnMemberParam()
            item.name = nameArray[x]
            item.leader = leaderArray[x]
            item.info = infoArray[x]
            listItems.add(item)
            adapter.setData(listItems)
            adapter.notifyDataSetChanged()
        }
    }

    private fun partItemClicked(member : JdihnMemberParam) {
        Toast.makeText(context, "${member.name} Clicked", Toast.LENGTH_SHORT).show()
    }
}