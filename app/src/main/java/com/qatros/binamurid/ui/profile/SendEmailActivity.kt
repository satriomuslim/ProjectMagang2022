package com.qatros.binamurid.ui.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.qatros.binamurid.data.remote.request.ResendEmailRequest
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.otp.OtpActivity
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivitySendEmailBinding
import org.koin.android.ext.android.inject

class SendEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySendEmailBinding

    private val viewModel: ProfileViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = ResendEmailRequest(
            email = SharedPreference(this).userEmail
        )
        viewModel.resendEmail(data)
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeSendEmailSuccess().observe(this@SendEmailActivity) {
                it.getContentIfNotHandled()?.let {
                    val dialog = Dialog(this@SendEmailActivity)
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    dialog.setContentView(R.layout.popup_add_email)
                    dialog.show()

                    Handler(Looper.getMainLooper()).postDelayed({
                        dialog.dismiss()
                        startActivity(Intent(this@SendEmailActivity, OtpActivity::class.java).putExtra(OtpActivity.PARAM, 2))
                        finish()
                    }, 1000)
                }
            }
        }
    }
}