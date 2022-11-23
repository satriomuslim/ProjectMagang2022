package com.qatros.qtn_bina_murid.ui.parent.daily

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