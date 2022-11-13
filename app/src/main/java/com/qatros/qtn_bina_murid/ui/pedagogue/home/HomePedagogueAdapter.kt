package com.qatros.qtn_bina_murid.ui.pedagogue.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.DataItem

class HomePedagogueAdapter(val dailyList: List<DataItem?>) : RecyclerView.Adapter<HomePedagogueAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var topLine : View = itemView.findViewById(R.id.vw_top)
        var bottomLine : View = itemView.findViewById(R.id.vw_bottom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_parent, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val main = dailyList[position]
        if(position == 0) {
            holder.topLine.isGone = true
        }

        if(position == (dailyList.size - 1)) {
            holder.bottomLine.isGone = true
        }
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
}