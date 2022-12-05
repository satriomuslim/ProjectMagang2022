package com.qatros.qtn_bina_murid.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityChatBinding
import org.koin.android.ext.android.inject

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private val viewModel : ChatViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeGetPrivateRoomSuccess().observe(this@ChatActivity) {
                it.getContentIfNotHandled()?.let { data ->

                }
            }
        }
    }
}