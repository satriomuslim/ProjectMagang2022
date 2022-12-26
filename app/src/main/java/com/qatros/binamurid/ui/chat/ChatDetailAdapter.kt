package com.qatros.binamurid.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qatros.binamurid.data.remote.response.User_available
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.R

class ChatDetailAdapter(val roomList: List<User_available>, private var clickListener : onItemClick) : RecyclerView.Adapter<ChatDetailAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var name: TextView = itemView.findViewById(R.id.tv_nama)
        var image: ImageView = itemView.findViewById(R.id.img_user)
        fun itemClick(data: User_available, action : onItemClick) {
            itemView.setOnClickListener{
                action.setItemClick(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val room = roomList[position]
        with(holder) {
            name.text = room.fullname
            image.loadImageUser(room.avatar)
            itemClick(room, clickListener)
        }
    }

    override fun getItemCount(): Int {
        return roomList.size
    }

    interface onItemClick {
        fun setItemClick(data: User_available, position: Int)
    }

}