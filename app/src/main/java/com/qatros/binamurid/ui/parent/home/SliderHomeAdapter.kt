package com.qatros.binamurid.ui.parent.home

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.recyclerview.widget.RecyclerView
import com.qatros.binamurid.R

class SliderHomeAdapter() : RecyclerView.Adapter<SliderHomeAdapter.ListViewHolder>() {
    var lst_images = intArrayOf(
        R.drawable.ill_home_parent,
        R.drawable.ill_home_2
    )

    // list of titles
    var lst_title = arrayOf(
        "Metode Lain",
        "Switch"
    )

    // list of descriptions
    var lst_description = arrayOf(
        "Kamu juga bisa membuka Aplikasi lewat Halaman Website",
        "Kamu juga bisa jadi Memantau Anak kamu atau menjadi Pedagogue"
    )

    // list of background colors
    var lst_backgroundcolor = intArrayOf(
        R.color.yellowHome,
        R.color.blueHome
    )

    var lst_imageColor = intArrayOf(
        R.drawable.ic_bg_home,
        R.drawable.ic_bg_home_2
    )

    var buttonIndicator = arrayOf(
        false, true
    )

    inner class ListViewHolder(listView : View) : RecyclerView.ViewHolder(listView) {
        var layout: ConstraintLayout = itemView.findViewById(R.id.cl_parent)
        val imgslide: ImageView = itemView.findViewById(R.id.iv_banner)
        val imgBackground: ImageView = itemView.findViewById(R.id.iv_background)
        val txttitle : TextView = itemView.findViewById(R.id.tv_title_banner)
        val description : TextView  = itemView.findViewById(R.id.tv_desc_banner)
        val buttonIn : TextView  = itemView.findViewById(R.id.btn_in_web)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_slider_home, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
       with(holder) {
           layout.backgroundTintList = ColorStateList.valueOf(itemView.context.resources.getColor(lst_backgroundcolor[position]))
           imgslide.setImageResource(lst_images[position])
           txttitle.text = lst_title[position]
           description.text = lst_description[position]
           buttonIn.isGone = buttonIndicator[position]
           imgBackground.setImageResource(lst_imageColor[position])
       }
    }

    override fun getItemCount(): Int {
        return lst_title.size
    }
}