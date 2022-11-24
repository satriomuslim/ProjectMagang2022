package com.qatros.qtn_bina_murid.ui.profile

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.FragmentEditProfileBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.utils.toast
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

//        binding.btnSaveDataProfileParent.setOnClickListener {
//            customDialog()
//        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }


//    private fun customDialog() {
//        val dialog = Dialog(requireActivity())
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.setContentView(R.layout.popup_confirm_email)
//
//        val btnClose = dialog.findViewById<Button>(R.id.btn_confirm_email)
//            btnClose.setOnClickListener {
//                dialog.dismiss()
//            }
//        dialog.show()
//    }

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

                val dialog = Dialog(requireActivity())
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.popup_confirm_email)

                val btnClose = dialog.findViewById<Button>(R.id.btn_confirm_email)
                btnClose.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
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