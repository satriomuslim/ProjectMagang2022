package com.qatros.qtn_bina_murid.ui.parent.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R

class HomeScanListAdapter(val childList: List<String>, private var clickListener : onItemClick) : RecyclerView.Adapter<HomeScanListAdapter.ListViewHolder>() {

    private var selectedPosition = -1

    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var name: TextView = itemView.findViewById(R.id.tv_nama)
        var image: ImageView = itemView.findViewById(R.id.img_user)
        var layout: ConstraintLayout = itemView.findViewById(R.id.cl_item)

        fun itemClick(data: String, action : onItemClick) {
            itemView.setOnClickListener{
                layout.setBackgroundColor(itemView.context.resources.getColor(R.color.blue))
                name.setTextColor(itemView.context.resources.getColor(R.color.white))
                action.setItemClick(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = childList[position]
        if (selectedPosition == position) {
            holder.layout.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.blue))
            holder.name.setTextColor(holder.itemView.context.resources.getColor(R.color.white))
        } else {
            holder.layout.setBackgroundColor(holder.itemView.context.resources.getColor(R.color.white))
            holder.name.setTextColor(holder.itemView.context.resources.getColor(R.color.black))
        }
        holder.itemView.setOnClickListener { v ->
            if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
            selectedPosition = holder.adapterPosition
            notifyItemChanged(selectedPosition)
           clickListener.setItemClick(child, position)
        }
//        with(holder) {
//            name.text = child.fullName
//            image.loadImageUser(child.avatar)
//            itemClick(child, clickListener)
//        }
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    interface onItemClick {
        fun setItemClick(data: String, position: Int)
    }
}