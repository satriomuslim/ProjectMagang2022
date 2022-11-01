package com.qatros.qtn_bina_murid.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityLandingBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.pedagogue.navigation.NavigationPedagogueActivity

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.btnNextLanding.setOnClickListener{
            startActivity(Intent(this, LandingNextActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    override fun onStart() {
        super.onStart()
        val isLogin = SharedPreference(this).isLogin
        val role = SharedPreference(this).userRole
        if(isLogin) {
            if (role == 1) {
                startActivity(Intent(this, NavigationParentActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, NavigationPedagogueActivity::class.java))
                finish()
            }
        }
    }
}