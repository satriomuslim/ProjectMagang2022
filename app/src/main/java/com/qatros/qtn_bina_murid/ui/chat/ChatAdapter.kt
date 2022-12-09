package com.qatros.qtn_bina_murid.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.DataItem
import com.qatros.qtn_bina_murid.data.remote.response.Private_room
import com.qatros.qtn_bina_murid.data.remote.response.User_available
import com.qatros.qtn_bina_murid.ui.history.HistoryAdapter
import com.qatros.qtn_bina_murid.utils.loadImageUser
import java.text.SimpleDateFormat

class ChatAdapter(val roomList: List<Private_room>, private var clickListener : onItemClick) : RecyclerView.Adapter<ChatAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        val name : TextView = itemView.findViewById(R.id.tv_name_chat_list)
        val lastChat: TextView = itemView.findViewById(R.id.tv_last_chat_list)
        var image: ImageView = itemView.findViewById(R.id.iv_profile_chat_list)
        fun itemClick(data: Private_room, action : onItemClick) {
            itemView.setOnClickListener{
                action.setItemClick(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val room = roomList[position]
        with(holder) {
            name.text = room?.name
            lastChat.text = room?.last_message?.content
            itemClick(room, clickListener)
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    interface onItemClick {
        fun setItemClick(data: Private_room, position: Int)
    }

}