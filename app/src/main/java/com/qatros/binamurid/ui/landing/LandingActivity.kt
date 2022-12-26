package com.qatros.binamurid.ui.landing

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.login.LoginActivity
import com.qatros.binamurid.ui.parent.navigation.NavigationParentActivity
import com.qatros.binamurid.ui.pedagogue.navigation.NavigationPedagogueActivity
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityLandingBinding

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

        binding.btnSkipLanding.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
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