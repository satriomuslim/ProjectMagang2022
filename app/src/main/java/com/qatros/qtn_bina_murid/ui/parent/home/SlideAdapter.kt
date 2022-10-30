package com.qatros.qtn_bina_murid.ui.parent.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isGone
import androidx.viewpager.widget.PagerAdapter
import com.qatros.qtn_bina_murid.R
import kotlinx.coroutines.NonDisposableHandle.parent


class SlideAdapter(context: Context) : PagerAdapter() {
    var context: Context
    var inflater: LayoutInflater? = null

    // list of images
    var lst_images = intArrayOf(
        R.drawable.ill_home_parent,
        R.drawable.ill_home_parent
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
        true, false
    )

    override fun getCount(): Int {
        return lst_title.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_slider_home, container, false)
        val layoutslide = view.findViewById(R.id.cl_parent) as ConstraintLayout
        val imgslide: ImageView = view.findViewById(R.id.iv_banner) as ImageView
        val imgBackground: ImageView = view.findViewById(R.id.iv_background) as ImageView
        val txttitle = view.findViewById(R.id.tv_title_banner) as TextView
        val description = view.findViewById(R.id.tv_desc_banner) as TextView
        val buttonIn = view.findViewById(R.id.btn_in_web) as TextView
        layoutslide.setBackgroundColor(lst_backgroundcolor[position])
        imgslide.setImageResource(lst_images[position])
        txttitle.text = lst_title[position]
        description.text = lst_description[position]
        buttonIn.isGone = buttonIndicator[position]
        imgBackground.setImageResource(lst_imageColor[position])
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    init {
        this.context = context
    }
}