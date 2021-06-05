package com.dicoding.picodiploma.kaidahapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.picodiploma.kaidahapp.adapter.SubjectAdapter
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentSubjectBinding
import com.dicoding.picodiploma.kaidahapp.entity.SubjectParam

class SubjectFragment : Fragment() {

    private lateinit var _binding: FragmentSubjectBinding
    private val binding get() = _binding
    private lateinit var adapter: SubjectAdapter
    var categoryArray = arrayOf("Pajak", "Kehutanan", "Pertanian", "Perairan", "Militer")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSubjectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.rvCategory.layoutManager = GridLayoutManager(context, 4)
//        adapter = SubjectAdapter { category : SubjectParam -> partItemClicked(category) }
//        binding.rvCategory.adapter = adapter
//
//        inputData()
    }

//    private fun inputData(){
//        val listItems = ArrayList<SubjectParam>()
//        for (x in 0..categoryArray.size-1){
//            val item = SubjectParam()
//            item.category = categoryArray[x]
//            listItems.add(item)
//            adapter.setData(listItems)
//            adapter.notifyDataSetChanged()
//        }
//    }
//
//    private fun partItemClicked(category : SubjectParam) {
//        Toast.makeText(context, "${category.category} Clicked", Toast.LENGTH_SHORT).show()
//    }
}