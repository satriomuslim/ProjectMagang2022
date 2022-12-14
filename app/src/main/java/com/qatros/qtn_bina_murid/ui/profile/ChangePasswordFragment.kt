package com.qatros.qtn_bina_murid.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.ChangePasswordRequest
import com.qatros.qtn_bina_murid.databinding.FragmentChangePasswordBinding
import com.qatros.qtn_bina_murid.databinding.FragmentEditProfileBinding
import com.qatros.qtn_bina_murid.databinding.FragmentSetNewPasswordBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ChangePasswordFragment : Fragment() {
    private lateinit var binding: FragmentChangePasswordBinding

    private val viewModel: ProfileViewModel by sharedViewModel()

    private val onBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(onBackCallback)

        binding.apply {
            edOldPassword.addTextChangedListener(loginTextWatcher)
            edNewPassword.addTextChangedListener(loginTextWatcher)
        }
        init()
        observeData()
    }

    private fun init() {
        binding.btnSaveNewPassword.setOnClickListener{
            val data = ChangePasswordRequest(
                oldPassword = binding.edOldPassword.text.toString(),
                newPassword = binding.edNewPassword.text.toString()
            )
            val token = SharedPreference(requireContext()).userToken

            viewModel.editPassword(token, data)
        }
    }

    private fun observeData() {
        viewModel.observeChangePasswordSuccess().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { success ->
                if(success) {
                    requireContext().toast("Change Password Success")
                    findNavController().popBackStack()
                } else {
                    requireContext().toast("Change Password Error")
                }
            }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply {
                when {
                    edOldPassword.text!!.isEmpty() -> {
                        edOldPassword.error = "Old Password Required"
                    }
                    edNewPassword.text!!.isEmpty() -> {
                        edNewPassword.error = "New Password Required"
                    }
                    else -> {

                    }

                }
                btnSaveNewPassword.isEnabled =
                    edOldPassword.text!!.isNotEmpty() && edNewPassword.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edOldPassword.text?.isBlank()?.not() == true && edNewPassword.text?.isBlank()
                        ?.not() == true
                ) {
                    btnSaveNewPassword.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnSaveNewPassword.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }
}