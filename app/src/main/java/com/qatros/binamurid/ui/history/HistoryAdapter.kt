package com.qatros.binamurid.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qatros.binamurid.data.remote.response.DataItem
import com.qatros.binamurid.R
import java.text.SimpleDateFormat

class HistoryAdapter(val historyList: List<DataItem?>) : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        val descHistory : TextView = itemView.findViewById(R.id.tv_text_history)
        val dateHistory: TextView = itemView.findViewById(R.id.tv_date_history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = historyList[position]
        with(holder) {
            val pedagogue = history?.parameters?.activity?.pedagogueName
            val children = history?.parameters?.activity?.childrenName
            descHistory.text = "Pedagogue $pedagogue sudah mengisi catatan $children"
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+07:00")
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val newDateTime: String = formatter.format(parser.parse(history?.createdAt))
            dateHistory.text = newDateTime
        }
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

}