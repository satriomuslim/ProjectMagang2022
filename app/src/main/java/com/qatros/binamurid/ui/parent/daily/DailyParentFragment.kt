package com.qatros.binamurid.ui.parent.daily

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.data.remote.response.Children
import com.qatros.binamurid.data.remote.response.Pedagogue
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.parent.childProfile.ChildProfileActivity
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.databinding.FragmentDailyParentBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class DailyParentFragment : Fragment(), DateAdapter.onItemClick {

    private val dates = ArrayList<Date>()
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH).time
    private lateinit var dateDefault: String
    private val viewModel: DailyParentViewModel by inject()

    private lateinit var binding: FragmentDailyParentBinding
    private lateinit var token: String
    private var childrenId: Int = 0
    private var pedagogueId: Int = 0
    private lateinit var pedagogueName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyParentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        token = SharedPreference(requireContext()).userToken
        viewModel.getChildList(token, "parent")
        binding.pbDailyParent.isGone = false
        dateDefault = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeGetChildListSuccess().observe(viewLifecycleOwner) { data ->
                data.getContentIfNotHandled().let {
                    if(it?.data.isNullOrEmpty()) {
                        Log.e("TAG", "observeData: TEST", )
                        binding.pbDailyParent.isGone = true
                        Toast.makeText(
                            requireContext(),
                            "Anak tidak ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val adapter = it?.data?.let { it1 ->
                            SpinChildAdapter(
                                requireContext(),
                                android.R.layout.simple_spinner_item,
                                it1
                            )
                        }
                        binding.spChild.adapter = adapter

                        binding.spChild.onItemSelectedListener =
                            object : AdapterView.OnItemSelectedListener {
                                override fun onItemSelected(
                                    adapterView: AdapterView<*>?, view: View?,
                                    position: Int, id: Long
                                ) {
                                    val child: Children = adapter?.getItem(position) ?: Children()
                                    childrenId = child.childrenId
                                    with(binding) {
                                        tvNameDailyReport.text = child.fullName
                                        tvSubjectChildDailyReport.text = child.school
                                        ivProfileDailyReport.loadImageUser(child.avatar)
                                        btnToProfileChild.setOnClickListener {
                                            startActivity(
                                                Intent(
                                                    activity,
                                                    ChildProfileActivity::class.java
                                                ).putExtra(ChildProfileActivity.CHILD_DATA, child)
                                            )
                                        }
                                    }
                                    getPedagogue(token, child.childrenId)
                                }

                                override fun onNothingSelected(adapter: AdapterView<*>?) {

                                }
                            }
                    }
                }
            }

            observeGetPedagogueSuccess().observe(viewLifecycleOwner) {
                if (it?.data.isNullOrEmpty()) {
                    binding.pbDailyParent.isGone = true
                    Toast.makeText(
                        requireContext(),
                        "Pedagogue tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val adapter = it?.data?.let { it1 ->
                        SpinnerPedagogueAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it1
                        )
                    }
                    binding.spPedagogue.adapter = adapter

                    binding.spPedagogue.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>?, view: View?,
                                position: Int, id: Long
                            ) {
                                val pedagogue: Pedagogue = adapter?.getItem(position) ?: Pedagogue()
                                pedagogueId = pedagogue.pedagogue_id
                                pedagogueName = pedagogue.fullname ?: ""
                                getReportParent(token, dateDefault, childrenId, pedagogueId)
                            }

                            override fun onNothingSelected(adapter: AdapterView<*>?) {

                            }
                        }
                }

            }

            observeError().observe(viewLifecycleOwner) {
                binding.pbDailyParent.isGone = true
                Toast.makeText(
                    requireContext(),
                    "Pedagogue tidak ditemukan",
                    Toast.LENGTH_SHORT
                ).show()
            }

            observeGetReportParent().observe(viewLifecycleOwner) { data ->
                binding.pbDailyParent.isGone = true
                binding.laNotFoundDailyParent.isGone = true
                binding.rvDetailDailyParent.isGone = false
                with(binding.rvDetailDailyParent) {
                    adapter = DailyReportAdapter(data?.data ?: listOf(), pedagogueName)
                    layoutManager = LinearLayoutManager(context)
                }
            }

            observeErrorGetReport().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    binding.pbDailyParent.isGone = true
                    binding.laNotFoundDailyParent.isGone = false
                    binding.rvDetailDailyParent.isGone = true
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
            btnAllParent.setOnClickListener {
                startActivity(Intent(activity, DailyParentsAllActivity::class.java))
            }
            with(rvDateDailyParent) {
                val dateAdapter = DateAdapter(dates, currentDate, this@DailyParentFragment)
                adapter = dateAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    override fun setItemClick(data: Date, position: Int) {
        dateDefault = SimpleDateFormat("yyyy-MM-dd").format(data.time)
        viewModel.getReportParent(token, dateDefault, childrenId, pedagogueId)
        binding.pbDailyParent.isGone = false
    }

    override fun onResume() {
        super.onResume()
        viewModel.getChildList(token, "parent")
        binding.pbDailyParent.isGone = false
    }
}