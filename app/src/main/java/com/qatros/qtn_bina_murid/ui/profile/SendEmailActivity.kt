package com.qatros.qtn_bina_murid.ui.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import android.widget.Toast
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.ResendEmailRequest
import com.qatros.qtn_bina_murid.databinding.ActivitySendEmailBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.otp.OtpActivity
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
                        startActivity(Intent(this@SendEmailActivity, OtpActivity::class.java))
                        finish()
                    }, 1000)
                }
            }
        }
    }
}