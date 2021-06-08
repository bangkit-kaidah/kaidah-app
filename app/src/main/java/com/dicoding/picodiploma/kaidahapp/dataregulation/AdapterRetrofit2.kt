package com.dicoding.picodiploma.kaidahapp.dataregulation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R

class AdapterRetrofit2(private var list: ArrayList<DataSerialized>): RecyclerView.Adapter<AdapterRetrofit2.RetrofitViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setterList(users: ArrayList<DataSerialized>) {
        list.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataSerialized)
    }

    inner class RetrofitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_title)
        var number: TextView = itemView.findViewById(R.id.numberRegulation)
        var status: TextView = itemView.findViewById(R.id.statusRegulation)
        var subject: TextView = itemView.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RetrofitViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_row_data, viewGroup, false)

        return RetrofitViewHolder(view)
    }

    override fun onBindViewHolder(holder: RetrofitViewHolder, position: Int) {
        val github = list[position]
        holder.tvName.text = github.title
        holder.number.text = "No. ${github.numberRegulation}"
        holder.status.text = github.status.name
        holder.subject.text = github.subject.name
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition])}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
}