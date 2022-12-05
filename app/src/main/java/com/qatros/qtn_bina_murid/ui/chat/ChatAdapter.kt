package com.qatros.qtn_bina_murid.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.DataItem
import com.qatros.qtn_bina_murid.data.remote.response.Private_room
import com.qatros.qtn_bina_murid.ui.history.HistoryAdapter
import java.text.SimpleDateFormat

class ChatAdapter(val roomList: List<Private_room?>) : RecyclerView.Adapter<ChatAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        val descHistory : TextView = itemView.findViewById(R.id.tv_text_history)
        val dateHistory: TextView = itemView.findViewById(R.id.tv_date_history)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = roomList[position]
        with(holder) {
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

}