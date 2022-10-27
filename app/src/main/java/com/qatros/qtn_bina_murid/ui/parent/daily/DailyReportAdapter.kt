package com.qatros.qtn_bina_murid.ui.parent.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R

class DailyReportAdapter(val dailyList: List<String>) : RecyclerView.Adapter<DailyReportAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_report_daily_parent, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val daily = dailyList[position]
    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
}