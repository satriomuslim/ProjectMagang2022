package com.qatros.qtn_bina_murid.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityLandingBinding
import com.qatros.qtn_bina_murid.databinding.ActivityLandingNextBinding
import com.qatros.qtn_bina_murid.ui.login.LoginActivity

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