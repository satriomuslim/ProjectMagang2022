package com.qatros.qtn_bina_murid.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.databinding.ActivityRegisterDetailBinding
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.utils.dialog.AestheticDialog
import com.qatros.qtn_bina_murid.utils.dialog.DialogAnimation
import com.qatros.qtn_bina_murid.utils.dialog.DialogStyle
import com.qatros.qtn_bina_murid.utils.dialog.OnDialogClickListener
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.android.ext.android.inject

class RegisterDetailActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegisterDetailBinding

    private val viewModel: RegisterViewModel by inject()

    private lateinit var role: String

    var successMessage: String = "The message was sent successfully!"
    var successTitle: String = "Success"

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
                        Toast.makeText(this@RegisterDetailActivity, "Register Success", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterDetailActivity, LoginActivity::class.java))
                        finish()
                    }
                }
            }

            observeError().observe(this@RegisterDetailActivity) {
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
            }

            btnParent.setOnClickListener {
                btnParent.setBackgroundResource(R.drawable.bg_rounded_blue_very_small_allradius)
                btnPedagogue.setBackgroundResource(R.drawable.bg_rounded_white_stoke_grey)
                btnRegister.isEnabled = true
                btnRegister.setBackgroundColor(resources.getColor(R.color.blue))
                btnParent.setTextColor(resources.getColor(R.color.white))
                btnPedagogue.setTextColor(resources.getColor(R.color.black))
                role = "parent"
            }

            btnPedagogue.setOnClickListener {
                btnPedagogue.setBackgroundResource(R.drawable.bg_rounded_blue_very_small_allradius)
                btnParent.setBackgroundResource(R.drawable.bg_rounded_white_stoke_grey)
                btnRegister.isEnabled = true
                btnRegister.setBackgroundColor(resources.getColor(R.color.blue))
                btnPedagogue.setTextColor(resources.getColor(R.color.white))
                btnParent.setTextColor(resources.getColor(R.color.black))
                role = "pedagogue"
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_register -> {
                AestheticDialog.Builder(this, DialogStyle.FLASH)
                    .setTitle(successTitle)
                    .setMessage(successMessage)
                    .setAnimation(DialogAnimation.SHRINK)
                    .setOnClickListener(object : OnDialogClickListener {
                        override fun onClick(dialog: AestheticDialog.Builder) {
                            dialog.dismiss()
                        }
                    })
                    .show()
            }
        }
    }

    companion object {
        val REGISTER_DATA = "RegisterDetailActivity.data"
    }
}