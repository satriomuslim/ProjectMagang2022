package com.qatros.qtn_bina_murid.ui.parent.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.Report
import org.w3c.dom.Text

class DailyReportAdapter(val dailyList: List<Report>, val pedagogue: String) : RecyclerView.Adapter<DailyReportAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        val name : TextView = itemView.findViewById(R.id.tv_name_pedagogue)
        val subject : TextView = itemView.findViewById(R.id.tv_subject)
        val desc: TextView = itemView.findViewById(R.id.tv_desc_from_pedagogue)
        val score: TextView = itemView.findViewById(R.id.tv_score)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_report_daily_parent, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val daily = dailyList[position]
        with(holder) {
            name.text = daily.fullname
            subject.text = daily.subject
            desc.text = daily.description
            score.text = daily.score.toString()
        }

    }

    override fun getItemCount(): Int {
        return dailyList.size
    }
}