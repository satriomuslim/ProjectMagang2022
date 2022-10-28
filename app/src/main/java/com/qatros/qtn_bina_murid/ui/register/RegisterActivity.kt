package com.qatros.qtn_bina_murid.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.databinding.ActivityRegisterBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.ui.login.LoginViewModel
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val viewModel: RegisterViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            etNameRegister.addTextChangedListener(loginTextWatcher)
            etEmailRegister.addTextChangedListener(loginTextWatcher)
            etTelpRegister.addTextChangedListener(loginTextWatcher)
            etPasswordRegister.addTextChangedListener(loginTextWatcher)
            etConfirmPasswordRegister.addTextChangedListener(loginTextWatcher)
        }

        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeRegisterSuccess().observe(this) { data ->
            SharedPreference(this).apply {
                userToken = "bearer ${data?.token}"
                isLogin = true
            }
            startActivity(Intent(this@RegisterActivity, NavigationParentActivity::class.java))
            finish()
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply {
                when {
                    etNameRegister.text!!.isEmpty() -> {
                        etNameRegister.error = "Name Required"
                    }
                    etEmailRegister.text!!.isEmpty() -> {
                        etEmailRegister.error = "Email Required"
                    }
                    etTelpRegister.text!!.isEmpty() -> {
                       etTelpRegister.error = "Number Telephone Required"
                    }
                    etPasswordRegister.text!!.isEmpty() -> {
                        etPasswordRegister.error = "Password Required"
                    }
                    etConfirmPasswordRegister.text!!.isEmpty() -> {
                        etConfirmPasswordRegister.error = "Confirmation Password Required"
                    }
                    else -> {

                    }

                }
                btnRegister.isEnabled =  etNameRegister.text!!.isNotEmpty() && etEmailRegister.text!!.isNotEmpty() && etTelpRegister.text!!.isNotEmpty() && etPasswordRegister.text!!.isNotEmpty() && etConfirmPasswordRegister.text!!.isNotEmpty()
            }
        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etNameRegister.text?.isBlank()?.not() == true && etEmailRegister.text?.isBlank()?.not() == true && etTelpRegister.text?.isBlank()?.not() == true && etPasswordRegister.text?.isBlank()?.not() == true && etConfirmPasswordRegister.text?.isBlank()?.not() == true)  {
                    btnRegister.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnRegister.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            btnRegister.setOnClickListener {
                val registerReq = RegisterRequest(
                    email = etEmailRegister.text.toString(),
                    password = etPasswordRegister.text.toString(),
                    no_hp = etTelpRegister.text.toString(),
                    fullname =  etNameRegister.text.toString()
                )
                viewModel.postRegister(registerReq)
            }
            btnBackFromLogin.setOnClickListener {
                finish()
            }
        }
    }
}