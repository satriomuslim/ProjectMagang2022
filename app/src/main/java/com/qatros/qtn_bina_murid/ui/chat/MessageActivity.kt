package com.qatros.qtn_bina_murid.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.AddChatRequest
import com.qatros.qtn_bina_murid.databinding.ActivityChatBinding
import com.qatros.qtn_bina_murid.databinding.ActivityMessageBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import org.koin.android.ext.android.inject

class MessageActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMessageBinding
    private val viewModel: ChatViewModel by inject()
    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val param = intent.getIntExtra(PARAM, 1)
        token = SharedPreference(this).userToken
        if (param == 2) {
            val userId = intent.getIntExtra(USER_ID, 0)
            viewModel.getAllMessage(token, userId)
        }
        init()
    }

    private fun init() {
        val roomId = intent.getIntExtra(ROOM_ID, 0)
        binding.btnSendMessage.setOnClickListener{
            val data = AddChatRequest(
                content = binding.etChat.text.toString()
            )
            viewModel.postMessage(token, roomId, data)
        }
    }

    companion object {
        const val PARAM = "messageActivity.param"
        const val USER_ID = "messageActivity.userid"
        const val ROOM_ID = "messageActivity.roomid"
    }
}
