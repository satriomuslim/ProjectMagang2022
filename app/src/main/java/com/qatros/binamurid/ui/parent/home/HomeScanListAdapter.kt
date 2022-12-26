package com.qatros.binamurid.ui.parent.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.qatros.binamurid.data.remote.response.Children
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.R

class HomeScanListAdapter(val childList: List<Children>, private var clickListener : onItemClick) : RecyclerView.Adapter<HomeScanListAdapter.ListViewHolder>() {

    private var selectedPosition = -1

    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var name: TextView = itemView.findViewById(R.id.tv_nama)
        var image: ImageView = itemView.findViewById(R.id.img_user)
        var layout: ConstraintLayout = itemView.findViewById(R.id.cl_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val child = childList[position]
        with(holder) {
            name.text = child.fullName
            image.loadImageUser(child.avatar)
            if (selectedPosition == position) {
                layout.setBackgroundColor(itemView.context.resources.getColor(R.color.blue))
                name.setTextColor(itemView.context.resources.getColor(R.color.white))
            } else {
                layout.setBackgroundColor(itemView.context.resources.getColor(R.color.white))
                name.setTextColor(itemView.context.resources.getColor(R.color.black))
            }
            itemView.setOnClickListener { v ->
                if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                clickListener.setItemClick(child, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    interface onItemClick {
        fun setItemClick(data: Children, position: Int)
    }
}