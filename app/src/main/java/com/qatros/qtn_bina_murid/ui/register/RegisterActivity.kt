package com.qatros.qtn_bina_murid.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.databinding.ActivityRegisterBinding
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.utils.toast
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
            etTelpRegister.addTextChangedListener(loginTextWatcher)
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
                btnNextDetail.isEnabled =  etNameRegister.text!!.isNotEmpty() && etEmailRegister.text!!.isNotEmpty() && etTelpRegister.text!!.isNotEmpty() && etPasswordRegister.text!!.isNotEmpty() && etConfirmPasswordRegister.text!!.isNotEmpty()
            }
        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etNameRegister.text?.isBlank()?.not() == true && etEmailRegister.text?.isBlank()?.not() == true && etTelpRegister.text?.isBlank()?.not() == true && etPasswordRegister.text?.isBlank()?.not() == true && etConfirmPasswordRegister.text?.isBlank()?.not() == true)  {
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
                    password = etPasswordRegister.text.toString(),
                    telp = etTelpRegister.text.toString()
                )
                startActivity(Intent(this@RegisterActivity, RegisterDetailActivity::class.java).putExtra(RegisterDetailActivity.REGISTER_DATA,regisData))
            }
            btnBackFromLogin.setOnClickListener {
                finish()
            }
        }
    }
}