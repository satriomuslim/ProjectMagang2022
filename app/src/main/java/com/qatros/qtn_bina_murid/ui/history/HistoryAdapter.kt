package com.qatros.qtn_bina_murid.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.DataItem

class HistoryAdapter(val historyList: List<DataItem?>) : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_history_layout, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val history = historyList[position]
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

}