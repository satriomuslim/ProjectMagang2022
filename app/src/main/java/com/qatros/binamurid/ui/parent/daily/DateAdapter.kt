package com.qatros.binamurid.ui.parent.daily

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.qatros.binamurid.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateAdapter(val dateList: List<Date>, private val currentDate: Date, private var clickListener : onItemClick) : RecyclerView.Adapter<DateAdapter.ListViewHolder>() {

    private var selectedPosition = -1

    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        val dayDate : TextView = itemView.findViewById(R.id.tv_day)
        val date : TextView = itemView.findViewById(R.id.tv_date_list)
        val layout: LinearLayout = itemView.findViewById(R.id.ll_parent_date)
        val viewCurrent: View = itemView.findViewById(R.id.vw_current)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_date, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val sdf = SimpleDateFormat("EEE", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = dateList[position]

        holder.viewCurrent.isGone = currentDate.toString() != cal.time.toString()

        try {
            val dayInWeek = sdf.parse(cal.time.toString()) as Date
            holder.dayDate.text = sdf.format(dayInWeek).toString()
        } catch (ex: ParseException) {
            Log.v("Exception", ex.localizedMessage ?: "")
        }
        holder.date.text = cal[Calendar.DAY_OF_MONTH].toString()

        with(holder) {
            if (selectedPosition == position) {
                layout.setBackgroundColor(itemView.context.resources.getColor(R.color.yellowWhite))
            } else {
                layout.setBackgroundColor(itemView.context.resources.getColor(R.color.white))
            }
            itemView.setOnClickListener { v ->
                if (selectedPosition >= 0) notifyItemChanged(selectedPosition)
                selectedPosition = adapterPosition
                notifyItemChanged(selectedPosition)
                clickListener.setItemClick(dateList[position], position)
            }
        }
    }

    override fun getItemCount(): Int {
        return dateList.size
    }

    interface onItemClick {
        fun setItemClick(data: Date, position: Int)
    }
}