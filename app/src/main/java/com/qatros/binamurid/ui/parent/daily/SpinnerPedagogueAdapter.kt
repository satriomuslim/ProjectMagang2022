package com.qatros.binamurid.ui.parent.daily

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.qatros.binamurid.data.remote.response.Pedagogue


class SpinnerPedagogueAdapter(
    context: Context, private val textViewResourceId: Int,
    private val values: List<Pedagogue>
) : ArrayAdapter<Pedagogue>(context, textViewResourceId, values) {
    override fun getCount(): Int {
        return values.size
    }

    override fun getItem(position: Int): Pedagogue {
        return values[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = super.getView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = values[position].fullname

        return label
    }

    override fun getDropDownView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {
        val label = super.getDropDownView(position, convertView, parent) as TextView
        label.setTextColor(Color.BLACK)
        label.text = values[position].fullname
        return label
    }
}