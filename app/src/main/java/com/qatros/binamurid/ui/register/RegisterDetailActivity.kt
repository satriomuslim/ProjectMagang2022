package com.qatros.binamurid.ui.register

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.qatros.binamurid.data.remote.request.RegisterRequest
import com.qatros.binamurid.ui.otp.OtpActivity
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityRegisterDetailBinding
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
                            startActivity(Intent(this@RegisterDetailActivity, OtpActivity::class.java).putExtra(OtpActivity.PARAM, 1))
                            finish()
                        }, 1000)

                    }
                }
            }

            observeIsErrorRegister().observe(this@RegisterDetailActivity) {
                it.getContentIfNotHandled()?.let { data ->
                    binding.pbRegister.isGone = true
                    this@RegisterDetailActivity.toast(data)
                }
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