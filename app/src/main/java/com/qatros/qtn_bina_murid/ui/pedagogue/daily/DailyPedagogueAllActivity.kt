package com.qatros.qtn_bina_murid.ui.pedagogue.daily

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityDailyPedagogueAllBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.daily.DailyReportAdapter
import org.koin.android.ext.android.inject

class DailyPedagogueAllActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDailyPedagogueAllBinding
    private lateinit var pedagogueName: String
    private val viewModel: DailyPedagogueViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailyPedagogueAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = SharedPreference(this).userToken
        pedagogueName = SharedPreference(this).userName
        viewModel.getAllReportPedagogue(token)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeGetAllReportPedagogue().observe(this@DailyPedagogueAllActivity) {
                with(binding.rvAllDailyPedagogue) {
                    adapter = ChildrenDailyReportAdapter(it?.data ?: listOf(), pedagogueName)
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }
}