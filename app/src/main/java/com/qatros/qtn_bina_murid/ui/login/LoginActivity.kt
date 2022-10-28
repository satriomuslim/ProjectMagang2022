package com.qatros.qtn_bina_murid.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isGone
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.UserLogin
import com.qatros.qtn_bina_murid.databinding.ActivityLoginBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.register.RegisterActivity
import com.qatros.qtn_bina_murid.ui.resetPassword.ResetPasswordActivity
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etEmailLogin.addTextChangedListener(loginTextWatcher)
        binding.etPasswordLogin.addTextChangedListener(loginTextWatcher)

        init()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeLoginSuccess().observe(this@LoginActivity) { data ->
                binding.pbLogin.isGone = true
                SharedPreference(this@LoginActivity).apply {
                    userToken = "bearer ${data?.token}"
                    isLogin = true
                }
                startActivity(Intent(this@LoginActivity, NavigationParentActivity::class.java))
                finish()
            }

            observeError().observe(this@LoginActivity) {
                binding.pbLogin.isGone = true
                this@LoginActivity.toast(it)
            }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply{
                when {
                    etEmailLogin.text!!.isEmpty() -> {
                       etEmailLogin.error = "Email Required"
                    }
                   etPasswordLogin.text!!.isEmpty() -> {
                        etEmailLogin.error = "Password Required"
                    }
                    else -> {

                    }

                }
                btnLogin.isEnabled =  etEmailLogin.text!!.isNotEmpty() && etPasswordLogin.text!!.isNotEmpty()
            }

        }

        override fun afterTextChanged(s: Editable) {

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

            btnLogin.setOnClickListener{
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