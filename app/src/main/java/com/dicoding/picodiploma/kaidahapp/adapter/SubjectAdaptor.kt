package com.dicoding.picodiploma.kaidahapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.databinding.ItemRvSubjectBinding
import com.dicoding.picodiploma.kaidahapp.datasubject.AdapterSubject
import com.dicoding.picodiploma.kaidahapp.datasubject.SubjectSerialized
import com.dicoding.picodiploma.kaidahapp.entity.FeaturedSubjectsResponse

class SubjectAdaptor(private var list: ArrayList<SubjectSerialized>): RecyclerView.Adapter<SubjectAdaptor.SubjectViewHolder> (){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setterList(users: ArrayList<SubjectSerialized>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(OnItemClickCallBack: OnItemClickCallback){
        this.onItemClickCallback = OnItemClickCallBack
    }

    interface OnItemClickCallback{
        fun onItemCLicked(data: SubjectSerialized)
    }

    inner class SubjectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var tvName: TextView = itemView.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_row_data, parent, false)
        return SubjectViewHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val data = list[position]
        holder.tvName.text = data.name
        holder.itemView.setOnClickListener { onItemClickCallback.onItemCLicked(list[holder.adapterPosition])}
    }

    override fun getItemCount(): Int {
        return list.size
    }
}