package com.qatros.binamurid.ui.chat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.data.remote.response.RoomData
import com.qatros.binamurid.data.remote.response.User_available
import com.qatros.binamurid.databinding.ActivityChatDetailBinding

class ChatDetailActivity : AppCompatActivity(), ChatDetailAdapter.onItemClick {
    private lateinit var binding: ActivityChatDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        val data = intent.getParcelableExtra<RoomData>(DATA)
        with(binding.rvChatDetailList) {
            adapter = data?.user_available?.let { ChatDetailAdapter(it, this@ChatDetailActivity) }
            layoutManager = LinearLayoutManager(this@ChatDetailActivity)
        }
    }

    companion object {
        const val DATA = "chatDetailActivity.data"
    }

    override fun setItemClick(data: User_available, position: Int) {
        startActivity(Intent(this, MessageActivity::class.java).putExtra(MessageActivity.USER_ID, data.id))
        finish()
    }
}