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
import com.qatros.qtn_bina_murid.databinding.FragmentResetPasswordBinding
import com.qatros.qtn_bina_murid.ui.login.LoginActivity

class ResetPasswordFragment : Fragment() {
    private lateinit var binding : FragmentResetPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            etEmailReset.addTextChangedListener(loginTextWatcher)
        }

        init()
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
                findNavController().navigate(R.id.action_resetPasswordFragment_to_checkEmailFragment)
            }

            btnResetToLogin.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}