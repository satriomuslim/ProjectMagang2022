package com.qatros.qtn_bina_murid.ui.otp

import `in`.aabhasjindal.otptextview.OTPListener
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.data.remote.request.ConfirmTokenRequest
import com.qatros.qtn_bina_murid.databinding.ActivityOtpBinding
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.android.ext.android.inject


class OtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpBinding

    private val viewModel: OtpViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observeData()
    }

    private fun init() {
        with(binding){
            btnConfirmToken.setOnClickListener{
                if (otpView.otp.isNullOrBlank().not()) {
                    val data = ConfirmTokenRequest(
                        confirmToken = otpView.otp ?: ""
                    )

                    viewModel.confirmToken(data)
                } else {
                    baseContext.toast("Silahkan Isi Token")
                }

            }
        }

    }

    private fun observeData() {
        with(viewModel) {
            observeConfirmTokenSuccess().observe(this@OtpActivity) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        Toast.makeText(this@OtpActivity, "Confirm Email Berhasil, Silahkan Login", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@OtpActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }
}