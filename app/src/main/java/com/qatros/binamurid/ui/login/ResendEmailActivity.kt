package com.qatros.binamurid.ui.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.qatros.binamurid.data.remote.request.ResendEmailRequest
import com.qatros.binamurid.ui.otp.OtpActivity
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityResendEmailBinding
import org.koin.android.ext.android.inject

class ResendEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResendEmailBinding

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResendEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            etEmailConfirm.addTextChangedListener(loginTextWatcher)
        }
        init()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeResendEmailSuccess().observe(this@ResendEmailActivity) {
                it.getContentIfNotHandled()?.let { success ->
                    if(success) {
                        binding.pbConfirmEmail.isGone = true
                        val dialog = Dialog(this@ResendEmailActivity)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_add_email)
                        dialog.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                            startActivity(Intent(this@ResendEmailActivity, OtpActivity::class.java))
                            finish()
                        }, 1000)
                    }
                }
            }

            observeError().observe(this@ResendEmailActivity) {
                it.getContentIfNotHandled()?.let {
                    binding.pbConfirmEmail.isGone = true
                    baseContext.toast(it)
                }
            }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply{
                when {
                    etEmailConfirm.text!!.isEmpty() -> {
                        etEmailConfirm.error = "Email Required"
                    }
                    else -> {

                    }

                }
                btnConfirmEmail.isEnabled =  etEmailConfirm.text!!.isNotEmpty()
            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etEmailConfirm.text?.isBlank()?.not() == true) {
                    btnConfirmEmail.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnConfirmEmail.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            btnConfirmEmail.setOnClickListener {
                val data = ResendEmailRequest(
                    email = etEmailConfirm.text.toString()
                )
                viewModel.resendEmail(data)
                pbConfirmEmail.isGone = false
            }

            btnCloseConfirm.setOnClickListener{
                finish()
            }
        }
    }
}