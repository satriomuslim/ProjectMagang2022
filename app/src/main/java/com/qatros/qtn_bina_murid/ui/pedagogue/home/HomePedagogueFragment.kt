package com.qatros.qtn_bina_murid.ui.pedagogue.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.databinding.FragmentHomePedagogueBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.home.HomeParentAdapter
import com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren.ScanChildrenActivity
import com.qatros.qtn_bina_murid.utils.requestPermission
import org.koin.android.ext.android.inject

class HomePedagogueFragment : Fragment() {

    private lateinit var binding: FragmentHomePedagogueBinding

    private val viewModel: HomePedagogueViewModel by inject()

    private val permissions =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePedagogueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = SharedPreference(requireContext()).userToken
        viewModel.getHomeParent(token)
        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeHomeSuccess().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                with(binding.rvDailyPendagogueUpdate){
                    adapter = HomePedagogueAdapter(data.data)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

    private fun init() {
        with(binding) {
            btnInviteChild.setOnClickListener{
                permissions.forEach {
                    requireActivity().let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, requireActivity())
                            return@setOnClickListener
                        }
                    }
                }
                startActivity(Intent(activity, ScanChildrenActivity::class.java))
            }
        }
    }
}