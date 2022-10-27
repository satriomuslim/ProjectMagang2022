package com.qatros.qtn_bina_murid.ui.resetPassword

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.ForgotPasswordRequest
import com.qatros.qtn_bina_murid.data.remote.response.ForgotPasswordResponse
import com.qatros.qtn_bina_murid.databinding.FragmentResetPasswordBinding
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
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

        binding.apply {
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