package com.qatros.binamurid.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.databinding.FragmentHistoryBinding
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
            binding.listChildPedagogue.text = "History Parent"
        } else {
            binding.listChildPedagogue.text = "History Pedagogue"
            viewModel.getHistoryPedagogue(token)
            binding.pbHistoryParent.isGone = false
        }
    }

    private fun observeData() {
        with(viewModel) {
            observeHistorySuccess().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    with(binding) {
                        pbHistoryParent.isGone = true
                        laNotFoundHistory.isGone = true
                        rvMain.isGone = false
                        with(rvMain) {
                            adapter = HistoryAdapter(data.data)
                            layoutManager = LinearLayoutManager(requireContext())
                        }
                    }
                }
            }

            observeError().observe(viewLifecycleOwner) {
                with(binding) {
                    pbHistoryParent.isGone = true
                    laNotFoundHistory.isGone = false
                    rvMain.isGone = true
                }
            }
        }
    }

}