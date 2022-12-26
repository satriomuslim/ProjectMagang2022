package com.qatros.binamurid.ui.pedagogue.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.chat.ChatActivity
import com.qatros.binamurid.ui.pedagogue.scanChildren.ScanChildrenActivity
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.utils.requestPermission
import com.qatros.binamurid.databinding.FragmentHomePedagogueBinding
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class HomePedagogueFragment : Fragment() {

    private lateinit var binding: FragmentHomePedagogueBinding

    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private lateinit var token: String
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
        token = SharedPreference(requireContext()).userToken
        viewModel.getHomePedagogue(token)
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

        viewModel.observeErrorGetReport().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                binding.laNotFoundHomePedagogue.isGone = false
                binding.rvDailyPendagogueUpdate.isGone = true
            }
        }
    }

    private fun init() {
        with(binding) {
            with(vpBannerHome) {
                adapter = SliderHomePedagogueAdapter()
            }
            ivChat.setOnClickListener{
                startActivity(Intent(activity, ChatActivity::class.java))
            }
            tvNameHome.text = "Hi, ${SharedPreference(requireContext()).userName}"
            tvEmailHome.text = SharedPreference(requireContext()).userEmail
            imgProfile.loadImageUser(SharedPreference(requireContext()).userAvatar)
            tvCurrentDate.text = SimpleDateFormat("EEEE, dd MMM yyyy").format(cal.time)
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

    override fun onResume() {
        super.onResume()
        viewModel.getHomePedagogue(token)
    }
}