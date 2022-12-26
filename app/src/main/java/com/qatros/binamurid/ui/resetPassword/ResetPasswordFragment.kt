package com.qatros.binamurid.ui.resetPassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.binamurid.data.remote.request.ForgotPasswordRequest
import com.qatros.binamurid.ui.login.LoginActivity
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.FragmentResetPasswordBinding
import org.koin.android.ext.android.inject

class ResetPasswordFragment : Fragment() {
    private lateinit var binding : FragmentResetPasswordBinding

    private val viewModel: ResetPasswordViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            etEmailReset.addTextChangedListener(loginTextWatcher)
        }

        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeForgotPasswordSuccess().observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_resetPasswordFragment_to_checkEmailFragment)
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply{
                when {
                    etEmailReset.text!!.isEmpty() -> {
                        etEmailReset.error = "Email Required"
                    }
                    else -> {

                    }

                }
                btnSendEmail.isEnabled =  etEmailReset.text!!.isNotEmpty()
            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (etEmailReset.text?.isBlank()?.not() == true) {
                    btnSendEmail.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnSendEmail.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            btnSendEmail.setOnClickListener {
                val resetPassReq = ForgotPasswordRequest(
                    email = etEmailReset.text.toString()
                )
                viewModel.postForgotPassword(resetPassReq)
            }

            btnResetToLogin.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}