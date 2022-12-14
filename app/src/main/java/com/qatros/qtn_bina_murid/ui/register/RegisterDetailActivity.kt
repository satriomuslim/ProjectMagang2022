package com.qatros.qtn_bina_murid.ui.register

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isGone
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.databinding.ActivityRegisterDetailBinding
import com.qatros.qtn_bina_murid.ui.landing.LandingActivity
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.android.ext.android.inject

class RegisterDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterDetailBinding

    private val viewModel: RegisterViewModel by inject()

    private lateinit var role: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observeData()


    }

    private fun observeData() {
        with(viewModel) {
            observeRegisterSuccess().observe(this@RegisterDetailActivity) {
                it.getContentIfNotHandled()?.let { success ->
                    if(success) {
                        binding.pbRegister.isGone = true
                        Toast.makeText(this@RegisterDetailActivity, "Register Success", Toast.LENGTH_SHORT).show()
                        val dialog = Dialog(this@RegisterDetailActivity)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_add_email)
                        dialog.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                            startActivity(Intent(this@RegisterDetailActivity, LoginActivity::class.java))
                            finish()
                        }, 1000)

                    }
                }
            }

            observeError().observe(this@RegisterDetailActivity) {
                binding.pbRegister.isGone = true
                this@RegisterDetailActivity.toast(it)
            }
        }
    }

    private fun init() {
        val data = intent.getParcelableExtra<RegisterData>(REGISTER_DATA)
        with(binding) {
            btnRegister.setOnClickListener {
                val registerReq = RegisterRequest(
                    email = data?.email ?: "",
                    password = data?.password ?: "",
                    fullname = data?.fullName ?: "",
                    role = role
                )
                viewModel.postRegister(registerReq)
                pbRegister.isGone = false
            }

            btnParent.setOnClickListener {
                btnParent.setBackgroundResource(R.drawable.bg_rounded_blue_very_small_allradius)
                btnPedagogue.setBackgroundResource(R.drawable.bg_rounded_white_stoke_blue_bold)
                btnRegister.isEnabled = true
                btnRegister.setBackgroundColor(resources.getColor(R.color.blue))
                btnParent.setTextColor(resources.getColor(R.color.white))
                btnPedagogue.setTextColor(resources.getColor(R.color.black))
                role = "parent"
            }

            btnPedagogue.setOnClickListener {
                btnPedagogue.setBackgroundResource(R.drawable.bg_rounded_blue_very_small_allradius)
                btnParent.setBackgroundResource(R.drawable.bg_rounded_white_stoke_blue_bold)
                btnRegister.isEnabled = true
                btnRegister.setBackgroundColor(resources.getColor(R.color.blue))
                btnPedagogue.setTextColor(resources.getColor(R.color.white))
                btnParent.setTextColor(resources.getColor(R.color.black))
                role = "pedagogue"
            }
        }
    }

    companion object {
        val REGISTER_DATA = "RegisterDetailActivity.data"
    }
}