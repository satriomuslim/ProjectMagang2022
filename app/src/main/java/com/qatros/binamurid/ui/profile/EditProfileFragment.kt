package com.qatros.binamurid.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.FragmentEditProfileBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    private val viewModel: ProfileViewModel by sharedViewModel()

    private var finalFile: File? = null

    private val onBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(onBackCallback)
        binding.apply {
            edNamaLengkap.addTextChangedListener(loginTextWatcher)
            edEmail.addTextChangedListener(loginTextWatcher)
        }
        init()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun observeData() {
        viewModel.observeEditProfileSuccess().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                requireContext().toast("Success Edit")
                SharedPreference(requireContext()).apply {
                    userName = data.data.fullname ?: ""
                    userAddress = data.data.address
                    userAvatar = data.data.avatar
                }
                viewModel.sendData(data.data.fullname ?: "", data.data.avatar ?: "")
                findNavController().popBackStack()
            }
        }
    }

    private fun init() {
        with(binding) {
            btnSaveDataProfileParent.setOnClickListener {
                val token = SharedPreference(requireContext()).userToken
                val name = edNamaLengkap.text.toString().toRequestBody("text/plain".toMediaType())
                val email = edEmail.text.toString().toRequestBody("text/plain".toMediaType())

                viewModel.editProfile(token, name, email)
            }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply {
                when {
                    edNamaLengkap.text!!.isEmpty() -> {
                        edNamaLengkap.error = "Name Required"
                    }
                    edEmail.text!!.isEmpty() -> {
                        edEmail.error = "Address Required"
                    }
                    else -> {

                    }

                }
                btnSaveDataProfileParent.isEnabled =
                    edNamaLengkap.text!!.isNotEmpty() && edEmail.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edNamaLengkap.text?.isBlank()?.not() == true && edEmail.text?.isBlank()
                        ?.not() == true
                ) {
                    btnSaveDataProfileParent.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnSaveDataProfileParent.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

}