package com.dicoding.picodiploma.kaidahapp.adapter

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ItemRvRegulationBinding
import com.dicoding.picodiploma.kaidahapp.entity.RegulationParam

class RegulationAdapter(private var clickListener: (RegulationParam) -> Unit) : RecyclerView.Adapter<RegulationAdapter.regulationViewHolder>() {

    private val sData = ArrayList<RegulationParam>()

    fun setData(items: ArrayList<RegulationParam>) {
        sData.clear()
        sData.addAll(items)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): regulationViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_rv_regulation, viewGroup, false)
        return regulationViewHolder(mView)
    }

    override fun onBindViewHolder(regulationViewHolder: regulationViewHolder, position: Int) {
        regulationViewHolder.bind(sData[position], clickListener)
    }

    override fun getItemCount(): Int = sData.size

    inner class regulationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvRegulationBinding.bind(itemView)

        fun bind(regulation: RegulationParam, clickListener: (RegulationParam) -> Unit) {
            binding.tvSubjectName.text = regulation.subject?.name
            binding.tvRegulationName.text = regulation.judul_dokumen
            binding.tvStatusRegulation.text = regulation.status?.name
            if (regulation.id == -1) {
                binding.tvSubjectName.visibility = View.GONE
                binding.tvSubjectName.gravity = Gravity.CENTER_HORIZONTAL
                binding.tvStatusRegulation.visibility = View.GONE
            }

            itemView.setOnClickListener { clickListener(regulation)}
        }
    }

}