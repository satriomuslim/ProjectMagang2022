package com.qatros.qtn_bina_murid.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.RoomData
import com.qatros.qtn_bina_murid.data.remote.response.User_available
import com.qatros.qtn_bina_murid.databinding.ActivityChatBinding
import com.qatros.qtn_bina_murid.databinding.ActivityChatDetailBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import org.koin.android.ext.android.inject

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
        startActivity(Intent(this, MessageActivity::class.java).putExtra(MessageActivity.USER_ID, data.id).putExtra(MessageActivity.PARAM, 2))
        finish()
    }
}