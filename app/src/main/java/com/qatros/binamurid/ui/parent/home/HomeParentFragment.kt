package com.qatros.binamurid.ui.parent.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.chat.ChatActivity
import com.qatros.binamurid.ui.parent.child.FormChildActivity
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.databinding.FragmentHomeParentBinding
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.*

class HomeParentFragment : Fragment() {

    private lateinit var binding: FragmentHomeParentBinding

    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private val viewModel: HomeViewModel by inject()

    private lateinit var token: String

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
        token = SharedPreference(requireContext()).userToken
        viewModel.getHomeParent(token)
        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeHomeSuccess().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                with(binding.rvDailyParentUpdate) {
                    adapter = HomeParentAdapter(data.data)
                    layoutManager = LinearLayoutManager(requireContext())
                }
            }
        }

        viewModel.observeErrorGetReport().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                binding.laNotFoundHomeParent.isGone = false
                binding.rvDailyParentUpdate.isGone = true
            }
        }
    }

    private fun init() {
        with(binding) {
            tvNameHome.text = "Hi, ${SharedPreference(requireContext()).userName}"
            tvEmailHome.text = SharedPreference(requireContext()).userEmail
            imgProfile.loadImageUser(SharedPreference(requireContext()).userAvatar)
            tvCurrentDate.text = SimpleDateFormat("EEEE, dd MMM yyyy").format(cal.time)
            btnInvitePendagogue.setOnClickListener {
                startActivity(Intent(activity, HomeScanListActivity::class.java))
            }

            wrapButtonDaftarAnak.setOnClickListener {
                startActivity(Intent(activity, FormChildActivity::class.java))
            }

            with(vpBannerHome) {
                adapter = SliderHomeAdapter()
            }

            val client: OkHttpClient = OkHttpClient()
            icChat.setOnClickListener {
//                Log.d("PieSocket", "Connecting");
//
//                val request: Request = Request
//                    .Builder()
//                    .url("ws://bina-murid.fly.dev/cable")
//                    .build()
//                val listener = WebSocketListener()
//                val ws: WebSocket = client.newWebSocket(request, listener)

                startActivity(Intent(activity, ChatActivity::class.java))
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getHomeParent(token)
    }
}