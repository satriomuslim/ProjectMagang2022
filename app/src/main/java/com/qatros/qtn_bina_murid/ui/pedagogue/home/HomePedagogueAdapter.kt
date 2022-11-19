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
import com.qatros.qtn_bina_murid.utils.loadImageUser
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class HomePedagogueAdapter(val dailyList: List<DataItem?>) : RecyclerView.Adapter<HomePedagogueAdapter.ListViewHolder>() {
    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var topLine : View = itemView.findViewById(R.id.vw_top)
        var bottomLine : View = itemView.findViewById(R.id.vw_bottom)
        val desc: TextView = itemView.findViewById(R.id.tv_title_daily_update)
        val date: TextView = itemView.findViewById(R.id.tv_date_daily_update)
        val time: TextView = itemView.findViewById(R.id.tv_time_daily_update)
        val avatar: ImageView = itemView.findViewById(R.id.img_user_daily)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_parent, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val main = dailyList[position]
        with(holder) {
            val pedagogue = main?.parameters?.activity?.pedagogueName
            val children = main?.parameters?.activity?.childrenName
            desc.text = "Catatan $children dari Pedagogue $pedagogue"
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+07:00")
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            val timeFormatter = SimpleDateFormat("HH:mm")
            val newDate: String = formatter.format(parser.parse(main?.createdAt))
            val newTime: String = timeFormatter.format(parser.parse(main?.createdAt))
            date.text = newDate
            time.text = newTime
            holder.avatar.loadImageUser(main?.parameters?.activity?.avatarChildren)
        }
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