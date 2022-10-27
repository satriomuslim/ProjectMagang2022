package com.qatros.qtn_bina_murid.ui.parent.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R

class HomeScanListAdapter(val childList: List<String>) : RecyclerView.Adapter<HomeScanListAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_user_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val main = childList[position]

    }

    override fun getItemCount(): Int {
        return childList.size
    }
}