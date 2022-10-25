package com.qatros.qtn_bina_murid.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        binding.etNameRegister.addTextChangedListener(loginTextWatcher)
        binding.etEmailRegister.addTextChangedListener(loginTextWatcher)
        binding.etTelpRegister.addTextChangedListener(loginTextWatcher)
        binding.etPasswordRegister.addTextChangedListener(loginTextWatcher)
        binding.etConfirmPasswordRegister.addTextChangedListener(loginTextWatcher)

        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeRegisterSuccess().observe(this) { data ->
            SharedPreference(this).userToken = data?.token ?: ""
            startActivity(Intent(this@RegisterActivity, NavigationParentActivity::class.java))
            finish()
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            val name = binding.etNameRegister.text.toString().trim()
            val email = binding.etEmailRegister.text.toString().trim()
            val telp = binding.etTelpRegister.text.toString().trim()
            val password = binding.etPasswordRegister.text.toString().trim()
            val confpass = binding.etConfirmPasswordRegister.text.toString().trim()

            when {
                name.isEmpty() -> {
                    binding.etNameRegister.error = "Name Required"
                }
                email.isEmpty() -> {
                    binding.etEmailRegister.error = "Email Required"
                }
                telp.isEmpty() -> {
                    binding.etTelpRegister.error = "Number Telephone Required"
                }
                password.isEmpty() -> {
                    binding.etPasswordRegister.error = "Password Required"
                }
                confpass.isEmpty() -> {
                    binding.etConfirmPasswordRegister.error = "Conformation Password Required"
                }
                else -> {

                }

            }
            binding.btnRegister.isEnabled =  name.isNotEmpty() && email.isNotEmpty() && telp.isNotEmpty() && password.isNotEmpty() && confpass.isNotEmpty()

        }

        override fun afterTextChanged(s: Editable) {

        }

    }

    private fun init() {
        with(binding) {
            btnRegister.setOnClickListener {
                val registerReq = RegisterRequest(
                    email = etEmailRegister.text.toString(),
                    password = etPasswordRegister.text.toString(),
                    no_hp = etTelpRegister.text.toString().toInt(),
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