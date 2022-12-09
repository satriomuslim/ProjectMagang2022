package com.qatros.qtn_bina_murid.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.qatros.qtn_bina_murid.data.remote.request.AddChatRequest
import com.qatros.qtn_bina_murid.data.remote.response.Messages
import com.qatros.qtn_bina_murid.databinding.ActivityMessageBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import okhttp3.*
import org.koin.android.ext.android.inject

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private val viewModel: ChatViewModel by inject()
    private lateinit var messageData : MutableList<Messages>
    private lateinit var messageAdapter: MessageAdapter
    private var userId : Int = 0
    private lateinit var token: String
    lateinit var mSocket: Socket
    val gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = SharedPreference(this).userId
        token = SharedPreference(this).userToken
        val userId = intent.getIntExtra(USER_ID, 0)
        viewModel.getAllMessage(token, userId)
        binding.pbMessage.isGone = false
        observeData()
    }


    private fun observeData() {
        viewModel.observeGetAllMessageSuccess().observe(this) {
            it.getContentIfNotHandled()?.let { dataResponse ->
                binding.pbMessage.isGone = true
                messageData = dataResponse.data.messages.sortedBy { it.message_id }.toMutableList()
                messageAdapter = MessageAdapter(messageData, userId)
                with(binding.rvChat) {
                    adapter = messageAdapter
                    layoutManager = LinearLayoutManager(this@MessageActivity)
                    scrollToPosition(messageData.size - 1)
                }
                binding.btnSendMessage.setOnClickListener {
                    val data = AddChatRequest(
                        content = binding.etChat.text.toString()
                    )
                    viewModel.postMessage(token, dataResponse.data.room.room_id , data)
                }
            }
        }

        viewModel.observePostMessageSuccess().observe(this) {
            it.getContentIfNotHandled()?.let { dataResponse ->
                val newData = Messages(
                    userId = dataResponse.data.user_id,
                    message_id = dataResponse.data.id,
                    content = dataResponse.data.content,
                    created_at = dataResponse.data.created_at
                )
                messageAdapter.addMessage(newData)
                binding.etChat.setText("")
            }
        }
    }

    companion object {
        const val USER_ID = "messageActivity.userid"
    }
}
