package com.qatros.qtn_bina_murid.ui.parent.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.databinding.FragmentHistoryParentBinding

class HistoryParentFragment : Fragment() {

    private lateinit var binding : FragmentHistoryParentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryParentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            with(rvMain) {
                adapter = HistoryParentAdapter(listOf("", "", "", "", "", "","", "", "", "", "", ""))
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

}