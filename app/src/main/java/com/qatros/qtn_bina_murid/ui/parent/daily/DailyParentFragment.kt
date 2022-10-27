package com.qatros.qtn_bina_murid.ui.parent.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.databinding.FragmentDailyParentBinding


class DailyParentFragment : Fragment() {

    private lateinit var binding : FragmentDailyParentBinding

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
    }

    private fun init() {
        with(binding) {
            with(rvDateDailyParent) {
                adapter = DateAdapter(listOf("", "", "", "", "", "","", "", "", "", "", ""))
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            with(rvDetailDailyParent) {
                adapter = DailyReportAdapter(listOf("", "", "", "", ""))
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}