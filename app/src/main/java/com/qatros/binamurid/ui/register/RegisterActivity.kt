package com.qatros.binamurid.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityRegisterBinding
import org.koin.android.ext.android.inject


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private var role = ""

    private val viewModel: RegisterViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            etNameRegister.addTextChangedListener(loginTextWatcher)
            etEmailRegister.addTextChangedListener(loginTextWatcher)
            etPasswordRegister.addTextChangedListener(loginTextWatcher)
            etConfirmPasswordRegister.addTextChangedListener(loginTextWatcher)
        }

        init()
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
                    etPasswordRegister.text!!.isEmpty() -> {
                        etPasswordRegister.error = "Password Required"
                    }
                    etConfirmPasswordRegister.text!!.isEmpty() -> {
                        etConfirmPasswordRegister.error = "Confirmation Password Required"
                    }
                    else -> {

                    }

                }
                btnNextDetail.isEnabled =  etNameRegister.text!!.isNotEmpty() && etEmailRegister.text!!.isNotEmpty() && etPasswordRegister.text!!.isNotEmpty() && etConfirmPasswordRegister.text!!.isNotEmpty()
            }
        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etNameRegister.text?.isBlank()?.not() == true && etEmailRegister.text?.isBlank()?.not() == true && etPasswordRegister.text?.isBlank()?.not() == true && etConfirmPasswordRegister.text?.isBlank()?.not() == true)  {
                    btnNextDetail.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnNextDetail.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            btnNextDetail.setOnClickListener {
                val regisData = RegisterData(
                    fullName =  etNameRegister.text.toString(),
                    email = etEmailRegister.text.toString(),
                    password = etPasswordRegister.text.toString()
                )
                startActivity(Intent(this@RegisterActivity, RegisterDetailActivity::class.java).putExtra(RegisterDetailActivity.REGISTER_DATA,regisData))
            }
            btnBackFromLogin.setOnClickListener {
                finish()
            }
        }
    }
}