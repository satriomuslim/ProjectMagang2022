package com.qatros.binamurid.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qatros.binamurid.ui.login.LoginActivity
import com.qatros.binamurid.databinding.ActivityLandingNextBinding

class LandingNextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingNextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingNextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.btnNextLanding2.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}