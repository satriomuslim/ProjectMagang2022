package com.qatros.binamurid.ui.login

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.qatros.binamurid.data.remote.request.LoginRequest
import com.qatros.binamurid.data.remote.request.UserLogin
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.parent.navigation.NavigationParentActivity
import com.qatros.binamurid.ui.pedagogue.navigation.NavigationPedagogueActivity
import com.qatros.binamurid.ui.register.RegisterActivity
import com.qatros.binamurid.ui.resetPassword.ResetPasswordActivity
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityLoginBinding
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            etEmailLogin.addTextChangedListener(loginTextWatcher)
            etPasswordLogin.addTextChangedListener(loginTextWatcher)
        }

        init()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeLoginSuccess().observe(this@LoginActivity) {
                it.getContentIfNotHandled()?.let { data ->
                    binding.pbLogin.isGone = true
                    SharedPreference(this@LoginActivity).apply {
                        userToken = "bearer ${data.token}"
                        isLogin = true
                        userEmail = data.data.email
                        userId = data.data.user_id
                        userName = data.data.fullname
                        userAddress = data.data.address
                        userAvatar = data.data.avatar
                        userDate = data.data.dateofbirth
                        userSubject = data.data.subject
                        userConfirmEmail = data.data.confirmed
                        userListRole = data.data.role.toMutableSet()
                        if ((data.data.role[0]) == "parent") {
                            userRole = 1
                            startActivity(Intent(this@LoginActivity, NavigationParentActivity::class.java))
                            finish()
                        } else {
                            userRole = 2
                            startActivity(Intent(this@LoginActivity, NavigationPedagogueActivity::class.java))
                            finish()
                        }
                    }
                }
            }

            observeLoginError().observe(this@LoginActivity) {
                it.getContentIfNotHandled()?.let { data ->
                    binding.pbLogin.isGone = true
                    if(data.second == 403) {
                        val dialog = Dialog(this@LoginActivity)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_confirm_email)

                        val btnClose = dialog.findViewById<Button>(R.id.btn_confirm_email)
                        btnClose.setOnClickListener {
                            startActivity(Intent(this@LoginActivity, ResendEmailActivity::class.java))
                            dialog.dismiss()
//                            startActivity(Intent(this@LoginActivity, ))
                        }
                        dialog.show()
                    } else {
                        this@LoginActivity.toast(data.first)
                    }
                }
            }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply {
                when {
                    etEmailLogin.text!!.isEmpty() -> {
                        etEmailLogin.error = "Email Required"
                    }
                    etPasswordLogin.text!!.isEmpty() -> {
                        etPasswordLogin.error = "Password Required"
                    }
                    else -> {

                    }

                }
                btnLogin.isEnabled =
                    etEmailLogin.text!!.isNotEmpty() && etPasswordLogin.text!!.isNotEmpty()
            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etEmailLogin.text?.isBlank()?.not() == true && etPasswordLogin.text?.isBlank()
                        ?.not() == true
                ) {
                    btnLogin.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnLogin.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            btnLoginToRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            btnForgotPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ResetPasswordActivity::class.java))
            }

            btnLogin.setOnClickListener {
                val loginReq = LoginRequest(
                    user = UserLogin(
                        email = etEmailLogin.text.toString(),
                        password = etPasswordLogin.text.toString()
                    )
                )
                viewModel.postLogin(loginReq)
                pbLogin.isGone = false

            }
        }
    }
}