package com.qatros.binamurid.ui.otp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qatros.binamurid.data.remote.request.ConfirmTokenRequest
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.login.LoginActivity
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.databinding.ActivityOtpBinding
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
        val param = intent.getIntExtra(PARAM, 1)
        with(viewModel) {
            observeConfirmTokenSuccess().observe(this@OtpActivity) {
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        if (param == 1){
                            Toast.makeText(this@OtpActivity, "Confirm Email Berhasil, Silahkan Login", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@OtpActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@OtpActivity, "Confirm Email Berhasil", Toast.LENGTH_LONG).show()
                            SharedPreference(this@OtpActivity).userConfirmEmail = true
                            finish()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val PARAM = "OtpActivity.Param"
    }
}