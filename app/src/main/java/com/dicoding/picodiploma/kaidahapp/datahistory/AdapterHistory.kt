package com.dicoding.picodiploma.kaidahapp.datahistory

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.kaidahapp.R
import com.dicoding.picodiploma.kaidahapp.datadetail.DataHistory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class AdapterHistory(private var list: ArrayList<DataHistory>): RecyclerView.Adapter<AdapterHistory.HistoryViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setterList(users: ArrayList<DataHistory>) {
        list.clear()
        list.addAll(users)
        list.reverse()
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: DataHistory)
    }


    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_title)
        var subject: TextView = itemView.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row_datahistory, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val data = list[position]
        holder.tvName.text = data.titleDoc

        val firstApiFormat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val date = LocalDate.parse(data.dateDoc, firstApiFormat)
        val human_readable = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))

        holder.subject.text = human_readable
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(list[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
