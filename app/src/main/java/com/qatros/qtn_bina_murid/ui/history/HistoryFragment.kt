package com.qatros.qtn_bina_murid.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.databinding.FragmentHistoryBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment() {

    private lateinit var binding : FragmentHistoryBinding

    private val viewModel: HistoryViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        val role = SharedPreference(requireContext()).userRole
        val token = SharedPreference(requireContext()).userToken
        if (role == 1) {
            viewModel.getHistoryParent(token)
            binding.pbHistoryParent.isGone = false
        } else {
            viewModel.getHistoryPedagogue(token)
            binding.pbHistoryParent.isGone = false
        }
    }

    private fun observeData() {
        with(viewModel) {
            observeHistorySuccess().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    with(binding) {
                        with(rvMain) {
                            adapter = HistoryAdapter(data.data)
                            layoutManager = LinearLayoutManager(requireContext())
                        }
                        pbHistoryParent.isGone = true
                    }
                }
            }
        }
    }

}