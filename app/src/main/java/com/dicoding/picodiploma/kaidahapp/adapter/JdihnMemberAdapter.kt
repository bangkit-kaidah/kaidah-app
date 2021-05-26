package com.dicoding.picodiploma.kaidahapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ItemRvJdhinMemberBinding
import com.dicoding.picodiploma.kaidahapp.entity.JdihnMemberParam

class JdihnMemberAdapter(private val clickListener: (JdihnMemberParam) -> Unit) : RecyclerView.Adapter<JdihnMemberAdapter.memberViewHolder>() {
    private val mData = ArrayList<JdihnMemberParam>()

    fun setData(items: ArrayList<JdihnMemberParam>) {
        mData.clear()
        mData.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): memberViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_jdhin_member, viewGroup, false)
        return memberViewHolder(mView)
    }

    override fun onBindViewHolder(memberViewHolder: memberViewHolder, position: Int) {
        memberViewHolder.bind(mData[position], clickListener)
    }

    override fun getItemCount(): Int = mData.size

    inner class memberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvJdhinMemberBinding.bind(itemView)

        @SuppressLint("SetTextI18n")
        fun bind(member: JdihnMemberParam, clickListener: (JdihnMemberParam) -> Unit) {
            binding.tvJdihnMemberName.text = member.name
            binding.tvJdihnMemberLeader.text = member.leader
            binding.tvJdihnMemberInfo.text = member.info

            itemView.setOnClickListener { clickListener(member)}
        }
    }
}