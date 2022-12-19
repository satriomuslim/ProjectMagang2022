package com.qatros.qtn_bina_murid.ui.parent.daily

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityDailyParentAllBinding
import com.qatros.qtn_bina_murid.databinding.ActivityDailyPedagogueAllBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.pedagogue.daily.DailyPedagogueViewModel
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*


class DailyParentsAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyParentAllBinding
    private val viewModel: DailyParentViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyParentAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = SharedPreference(this).userToken
        viewModel.getAllReportParent(token)
        observeData()
    }

    private fun getDate() {
        val calendar = Calendar.getInstance()
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)
        val dateTime = Calendar.getInstance()
        DatePickerDialog(
            this, R.style.DialogTheme,
            { view, year, month, day ->
                dateTime.set(year, month, day)
                val dateFormater = SimpleDateFormat("yyyy-MM-dd").format(dateTime.time)
                binding.tvDateDetailReport.text = dateFormater

            },
            year, month, day
        ).show()

    }

    private fun observeData() {
        with(viewModel) {
            observeGetAllReportPedagogue().observe(this@DailyParentsAllActivity) {
                with(binding.rvMain) {
                    adapter = DailyReportAdapter(it?.data ?: listOf(), "User")
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }
}