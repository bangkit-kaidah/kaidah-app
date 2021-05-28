package com.dicoding.picodiploma.kaidahapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ItemRvCategoryBinding
import com.dicoding.picodiploma.kaidahapp.entity.CategoryParam

class CategoryAdapter(private val clickListener: (CategoryParam) -> Unit) : RecyclerView.Adapter<CategoryAdapter.categoryViewHolder>() {

    private val cData = ArrayList<CategoryParam>()

    fun setData(items: ArrayList<CategoryParam>) {
        cData.clear()
        cData.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): categoryViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_category, viewGroup, false)
        return categoryViewHolder(mView)
    }

    override fun onBindViewHolder(categoryViewHolder: categoryViewHolder, position: Int) {
        categoryViewHolder.bind(cData[position], clickListener)
    }

    override fun getItemCount(): Int = cData.size

    inner class categoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvCategoryBinding.bind(itemView)

        fun bind(category: CategoryParam, clickListener: (CategoryParam) -> Unit) {
            binding.tvCategoryName.text = category.category

            itemView.setOnClickListener { clickListener(category)}
        }
    }

}