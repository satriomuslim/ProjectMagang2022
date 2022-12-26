package com.qatros.binamurid.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.data.remote.response.Private_room
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.databinding.ActivityChatBinding
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