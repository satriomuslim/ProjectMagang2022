package com.qatros.qtn_bina_murid.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.Private_room
import com.qatros.qtn_bina_murid.databinding.ActivityChatBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import org.koin.android.ext.android.inject

class ChatActivity : AppCompatActivity(), ChatAdapter.onItemClick {
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
                    Log.e("TAG", "observeData: $data", )
                    with(binding.rvChatList) {
                        adapter = data.data.private_room?.let { it1 -> ChatAdapter(it1, this@ChatActivity) }
                        layoutManager = LinearLayoutManager(this@ChatActivity)
                    }

                    with(binding) {
                        btnNewChat.setOnClickListener{
                            startActivity(Intent(this@ChatActivity, ChatDetailActivity::class.java).putExtra(ChatDetailActivity.DATA, data.data))
                        }
                    }
                }
            }
        }
    }

    override fun setItemClick(data: Private_room, position: Int) {
        startActivity(Intent(this, MessageActivity::class.java).putExtra(MessageActivity.USER_ID, data.recipient_id))
    }
}