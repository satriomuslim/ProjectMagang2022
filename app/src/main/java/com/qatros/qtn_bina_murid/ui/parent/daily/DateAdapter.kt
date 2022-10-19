package com.qatros.qtn_bina_murid.ui.parent.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.ui.parent.home.HomeParentAdapter

class DateAdapter(val dateList: List<String>) : RecyclerView.Adapter<DateAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val date = dateList[position]
    }

    override fun getItemCount(): Int {
        return dateList.size
    }
}