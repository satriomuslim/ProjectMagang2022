package com.qatros.qtn_bina_murid.ui.parent.home

import android.content.Intent
import android.os.Build.ID
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.databinding.FragmentHomeParentBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.child.FormChildActivity
import com.qatros.qtn_bina_murid.utils.loadImageUser
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class HomeParentFragment : Fragment() {

    private lateinit var binding: FragmentHomeParentBinding

    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private val viewModel : HomeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeParentBinding.inflate(layoutInflater, container, false)
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
                with(binding.rvDailyParentUpdate){
                    adapter = HomeParentAdapter(data.data)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }
    }

    private fun init() {
        with(binding) {
            tvNameHome.text = "Hi, ${SharedPreference(requireContext()).userName}"
            tvEmailHome.text = SharedPreference(requireContext()).userEmail
            imgProfile.loadImageUser(SharedPreference(requireContext()).userAvatar)
            tvCurrentDate.text = SimpleDateFormat("EEEE, dd MMM yyyy").format(cal.time)
            btnInvitePendagogue.setOnClickListener{
                startActivity(Intent(activity, HomeScanListActivity::class.java))
            }

            wrapButtonDaftarAnak.setOnClickListener{
                startActivity(Intent(activity, FormChildActivity::class.java))
            }

            with(vpBannerHome) {
                adapter = SliderHomeAdapter()
            }
        }

    }
}