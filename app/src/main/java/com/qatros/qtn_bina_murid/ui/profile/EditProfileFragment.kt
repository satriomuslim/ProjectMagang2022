package com.qatros.qtn_bina_murid.ui.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.FragmentEditProfileBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.utils.toast
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File

class EditProfileFragment : Fragment() {

    private lateinit var binding : FragmentEditProfileBinding

    private val viewModel: ProfileViewModel by sharedViewModel()

    private var finalFile: File? = null

    private val onBackCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
            viewModel.indicatorProfile(true)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(onBackCallback)
        binding.apply{
            edNamaLengkap.addTextChangedListener(loginTextWatcher)
            edAlamat.addTextChangedListener(loginTextWatcher)
            edNomorHandphone.addTextChangedListener(loginTextWatcher)
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
        viewModel.observeImageFile().observe(viewLifecycleOwner) {
            Log.e("TA", "observeData: $it", )
            finalFile = it
        }
        viewModel.observeEditProfileSuccess().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                requireContext().toast("Success Edit")
                SharedPreference(requireContext()).apply {
                    userName = data.data.fullname ?: ""
                    userAddress = data.data.address
                    userTelp = data.data.phoneNumber ?: ""
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
                val userId = SharedPreference(requireContext()).userId
                val name = edNamaLengkap.text.toString().toRequestBody("text/plain".toMediaType())
                val telp = edNomorHandphone.text.toString().toRequestBody("text/plain".toMediaType())
                val address = edAlamat.text.toString().toRequestBody("text/plain".toMediaType())
                val dateOfBirth = SharedPreference(requireContext()).userDate?.toRequestBody("text/plain".toMediaType())
                val requestImageFile =
                    finalFile?.asRequestBody("image/jpg".toMediaTypeOrNull())
                val imageMultipart = requestImageFile?.let { it1 ->
                    MultipartBody.Part.createFormData(
                        "avatar_children",
                        finalFile?.name,
                        it1
                    )
                }
                if (dateOfBirth != null) {
                    viewModel.editProfile(token, userId, name, telp, address, dateOfBirth, imageMultipart)
                }
            }
        }
        viewModel.indicatorProfile(false)
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply{
                when {
                    edNamaLengkap.text!!.isEmpty() -> {
                        edNamaLengkap.error = "Name Required"
                    }
                    edAlamat.text!!.isEmpty() -> {
                        edAlamat.error = "Address Required"
                    }
                    edNomorHandphone.text!!.isEmpty() -> {
                        edNomorHandphone.error = "Number Phone Required"
                    }
                    else -> {

                    }

                }
                btnSaveDataProfileParent.isEnabled =  edNamaLengkap.text!!.isNotEmpty() && edAlamat.text!!.isNotEmpty()  && edNomorHandphone.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edNamaLengkap.text?.isBlank()?.not() == true && edAlamat.text?.isBlank()?.not() == true  && edNomorHandphone.text?.isBlank()?.not() == true) {
                    btnSaveDataProfileParent.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnSaveDataProfileParent.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.indicatorProfile(false)
    }

}