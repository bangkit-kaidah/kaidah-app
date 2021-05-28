package com.dicoding.picodiploma.kaidahapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.adapter.CategoryAdapter
import com.dicoding.picodiploma.kaidahapp.adapter.JdihnMemberAdapter
import com.dicoding.picodiploma.kaidahapp.databinding.FragmentCategoryBinding
import com.dicoding.picodiploma.kaidahapp.entity.CategoryParam
import com.dicoding.picodiploma.kaidahapp.entity.JdihnMemberParam

class CategoryFragment : Fragment() {

    private lateinit var _binding: FragmentCategoryBinding
    private val binding get() = _binding
    private lateinit var adapter: CategoryAdapter
    var categoryArray = arrayOf("Pajak", "Kehutanan", "Pertanian", "Perairan", "Militer")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategory.layoutManager = GridLayoutManager(context, 4)
        adapter = CategoryAdapter { category : CategoryParam -> partItemClicked(category) }
        binding.rvCategory.adapter = adapter

        inputData()
    }

    private fun inputData(){
        val listItems = ArrayList<CategoryParam>()
        for (x in 0..categoryArray.size-1){
            val item = CategoryParam()
            item.category = categoryArray[x]
            listItems.add(item)
            adapter.setData(listItems)
            adapter.notifyDataSetChanged()
        }
    }

    private fun partItemClicked(category : CategoryParam) {
        Toast.makeText(context, "${category.category} Clicked", Toast.LENGTH_SHORT).show()
    }
}