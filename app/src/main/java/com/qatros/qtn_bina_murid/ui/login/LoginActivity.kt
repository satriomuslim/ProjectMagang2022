package com.qatros.qtn_bina_murid.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.core.widget.addTextChangedListener
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityLandingBinding
import com.qatros.qtn_bina_murid.databinding.ActivityLoginBinding
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.register.RegisterActivity
import com.qatros.qtn_bina_murid.ui.resetPassword.ResetPasswordActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etEmailLogin.addTextChangedListener(loginTextWatcher)
        binding.etPasswordLogin.addTextChangedListener(loginTextWatcher)

        init()
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
                startActivity(Intent(this@LoginActivity, NavigationParentActivity::class.java))
            }
        }
    }
}