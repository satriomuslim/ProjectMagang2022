package com.qatros.qtn_bina_murid.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.ui.landing.LandingActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
           startActivity(Intent(this, LandingActivity::class.java))
        }, 2000)

    }
}