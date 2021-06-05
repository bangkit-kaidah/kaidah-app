package com.dicoding.picodiploma.kaidahapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ItemRvSubjectBinding
import com.dicoding.picodiploma.kaidahapp.entity.FeaturedSubjectsResponse

class SubjectAdapter(private var clickListener: (FeaturedSubjectsResponse) -> Unit) : RecyclerView.Adapter<SubjectAdapter.subjectViewHolder>() {

    private val sData = ArrayList<FeaturedSubjectsResponse>()

    fun setData(items: ArrayList<FeaturedSubjectsResponse>) {
        sData.clear()
        sData.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): subjectViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_subject, viewGroup, false)
        return subjectViewHolder(mView)
    }

    override fun onBindViewHolder(subjectViewHolder: subjectViewHolder, position: Int) {
        subjectViewHolder.bind(sData[position], clickListener)
    }

    override fun getItemCount(): Int = sData.size

    inner class subjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvSubjectBinding.bind(itemView)

        fun bind(subject: FeaturedSubjectsResponse, clickListener: (FeaturedSubjectsResponse) -> Unit) {
            binding.tvSubjectName.text = subject.name
            if (subject.subject_id == -1) {
                binding.tvTotalDocuments.visibility = View.GONE
            }
            binding.tvTotalDocuments.text = "("+ subject.total_documents + " dokumen)"

            itemView.setOnClickListener { clickListener(subject)}
        }
    }

}