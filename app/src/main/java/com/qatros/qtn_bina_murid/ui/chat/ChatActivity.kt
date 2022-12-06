package com.qatros.qtn_bina_murid.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityChatBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import org.koin.android.ext.android.inject

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel : ChatViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = SharedPreference(this).userToken
        viewModel.getPrivateRoom(token)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeGetPrivateRoomSuccess().observe(this@ChatActivity) {
                it.getContentIfNotHandled()?.let { data ->
                    with(binding.rvChatList) {
                        adapter = ChatAdapter(data.data.private_room)
                        layoutManager = LinearLayoutManager(this@ChatActivity)
                    }
                }
            }
        }
    }
}