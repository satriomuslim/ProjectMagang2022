package com.qatros.qtn_bina_murid.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.view.isGone
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.UserLogin
import com.qatros.qtn_bina_murid.databinding.ActivityLoginBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.pedagogue.navigation.NavigationPedagogueActivity
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

        binding.apply{
            etEmailLogin.addTextChangedListener(loginTextWatcher)
            etPasswordLogin.addTextChangedListener(loginTextWatcher)
        }

        init()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeLoginSuccess().observe(this@LoginActivity) { data ->
                binding.pbLogin.isGone = true
                if(data?.data?.email != "bobo@gmail.com") {
                    SharedPreference(this@LoginActivity).apply {
                        userToken = "bearer ${data?.token}"
                        isLogin = true
                        userRole = if((data?.data?.role?.get(0) ?: "") == "parent") {
                            1
                        } else {
                            2
                        }
                        userEmail = data?.data?.email ?: ""
                        userId = data?.data?.user_id ?: 0
                        userName = data?.data?.fullname ?: ""
                        userTelp = data?.data?.no_hp ?: ""
                        userAddress = data?.data?.address
                    }
                    startActivity(Intent(this@LoginActivity, NavigationParentActivity::class.java))
                    finish()
                } else {
                    SharedPreference(this@LoginActivity).apply {
                        userToken = "bearer ${data.token}"
                        isLogin = true
                        userRole = 2
                        userEmail = data.data.email
                        userId = data.data.user_id
                        userName = data.data.fullname
                        userTelp = data.data.no_hp
                        userAddress = data.data.address
                    }
                    startActivity(Intent(this@LoginActivity, NavigationPedagogueActivity::class.java))
                    finish()
                }
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
                        etPasswordLogin.error = "Password Required"
                    }
                    else -> {

                    }

                }
                btnLogin.isEnabled =  etEmailLogin.text!!.isNotEmpty() && etPasswordLogin.text!!.isNotEmpty()
            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etEmailLogin.text?.isBlank()?.not() == true && etPasswordLogin.text?.isBlank()?.not() == true) {
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

            btnLogin.setOnClickListener{
//                startActivity(Intent(this@LoginActivity, NavigationPedagogueActivity::class.java))
//                finish()
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