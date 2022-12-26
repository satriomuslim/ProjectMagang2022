package com.qatros.binamurid.ui.chat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.qatros.binamurid.data.remote.response.Messages
import com.qatros.binamurid.R

class MessageAdapter(val messageList: MutableList<Messages>, val user: Int) : RecyclerView.Adapter<MessageAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var textOpposite: TextView = itemView.findViewById(R.id.txt_chat_message_opposite)
        var textMine: TextView = itemView.findViewById(R.id.txt_chat_message_mine)
        var timeOpposite: TextView = itemView.findViewById(R.id.txt_chat_timestamp_opposite)
        var timeMine: TextView = itemView.findViewById(R.id.txt_chat_timestamp_mine)
        var parentOpposite : LinearLayout = itemView.findViewById(R.id.lin_opposite_chat)
        var parentMine : LinearLayout = itemView.findViewById(R.id.lin_my_chat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val message = messageList[position]
        Log.e("TAG", "getPrivateRoom:$message , $user", )
        with(holder) {
            if(message.userId == user) {
                parentOpposite.isGone = true
                textMine.text = message.content
                timeMine.text = message.created_at
            } else {
                parentMine.isGone = true
                textOpposite.text = message.content
                timeOpposite.text = message.created_at
            }
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    fun addMessage(data: Messages){
        messageList.add(data)
        notifyItemInserted(messageList.lastIndex)
    }

}