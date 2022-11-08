package com.qatros.qtn_bina_murid.ui.pedagogue.daily

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.Children
import com.qatros.qtn_bina_murid.databinding.FragmentDailyParentBinding
import com.qatros.qtn_bina_murid.databinding.FragmentDailyPedagogueBinding
import com.qatros.qtn_bina_murid.ui.parent.childProfile.ChildProfileActivity
import com.qatros.qtn_bina_murid.ui.parent.daily.DailyParentViewModel
import com.qatros.qtn_bina_murid.ui.parent.daily.DailyReportAdapter
import com.qatros.qtn_bina_murid.ui.parent.daily.DateAdapter
import com.qatros.qtn_bina_murid.ui.parent.daily.SpinChildAdapter
import com.qatros.qtn_bina_murid.ui.pedagogue.daily.DateAdapter.*
import com.qatros.qtn_bina_murid.utils.loadImageUser
import org.koin.android.ext.android.inject
import java.util.*

class DailyPedagogueFragment : Fragment(), onItemClick {

    private lateinit var binding: FragmentDailyPedagogueBinding

    private val dates = ArrayList<Date>()
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH).time
    private lateinit var dateDefault: String
    private val viewModel: DailyPedagogueViewModel by inject()
    private lateinit var token: String
    private var childrenId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyPedagogueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeGetChildListSuccess().observe(viewLifecycleOwner) {
                val adapter = it?.data?.let { it1 ->
                    SpinChildAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        it1
                    )
                }
                binding.spChildPedagogue.adapter = adapter

                binding.spChildPedagogue.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            adapterView: AdapterView<*>?, view: View?,
                            position: Int, id: Long
                        ) {
                            val child: Children = adapter?.getItem(position) ?: Children()
                            childrenId = child.childrenId
                        }

                        override fun onNothingSelected(adapter: AdapterView<*>?) {

                        }
                    }

            }

            observeGetReportPedagogue().observe(viewLifecycleOwner) { data ->
                with(binding.rvDetailDailyParent) {
                    adapter = DailyReportAdapter(data?.data ?: listOf(), "")
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    private fun init() {
        with(binding) {
            val monthCalendar = cal.clone() as Calendar
            val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
            while (dates.size < maxDaysInMonth) {
                dates.add(monthCalendar.time)
                monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            with(rvDateDailyDetailPedagogue) {
                val dateAdapter = DateAdapter(dates, currentDate, this@DailyPedagogueFragment)
                adapter = dateAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun setItemClick(data: Date, position: Int) {

    }
}