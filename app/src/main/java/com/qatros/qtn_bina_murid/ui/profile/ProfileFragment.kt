package com.qatros.qtn_bina_murid.ui.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.base.BaseFragment
import com.qatros.qtn_bina_murid.databinding.BottomAddImageBinding
import com.qatros.qtn_bina_murid.databinding.BottomImageReviewBinding
import com.qatros.qtn_bina_murid.databinding.FragmentProfileBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.utils.createCustomTempFile
import com.qatros.qtn_bina_murid.utils.loadImageUser
import com.qatros.qtn_bina_murid.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeData()
    }

    private fun observeData() {
        with(viewModel) {
            observeSendData().observe(viewLifecycleOwner) {
                with(binding) {
                    txtNameParent.text = it.first
                }
            }

            observeEditAvatarSuccess().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    binding.imgProfile.loadImageUser(data.data.avatar)
                    SharedPreference(requireContext()).userAvatar = data.data.avatar
                }
            }
        }
    }

    private fun init() {
        with(binding) {
            btnAddImageProfile.setOnClickListener {
                showBottomSheetDialog()
            }
            imgProfile.loadImageUser(SharedPreference(requireContext()).userAvatar)
            txtNameParent.text = SharedPreference(requireContext()).userName
            txtEmailParent.text = SharedPreference(requireContext()).userEmail
        }
    }

    private fun showBottomSheetDialog() {
        val dialogBinding = BottomAddImageBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        with(dialogBinding) {
            btnAddCamera.setOnClickListener {
                startTakePhoto()
                bottomSheetDialog.dismiss()
            }

            btnAddGalery.setOnClickListener {
                startGallery()
                bottomSheetDialog.dismiss()
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, requireContext())
            val requestImageFile =
                myFile.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultipart = requestImageFile.let { it1 ->
                MultipartBody.Part.createFormData(
                    "avatar",
                    myFile.name,
                    it1
                )
            }
            val dialogBinding = BottomImageReviewBinding.inflate(layoutInflater)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(dialogBinding.root)
            bottomSheetDialog.show()
            with(dialogBinding) {
                ivImageReview.loadImageUser(selectedImg.path)
                btnSaveAvatar.setOnClickListener{
                    val token = SharedPreference(requireContext()).userToken
                    viewModel.editAvatar(token, imageMultipart)
                    bottomSheetDialog.dismiss()
                }
            }
        }
    }

    private lateinit var currentPhotoPath: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val result = BitmapFactory.decodeFile(myFile.path)
            val requestImageFile =
                myFile.asRequestBody("image/jpg".toMediaTypeOrNull())
            val imageMultipart = requestImageFile.let { it1 ->
                MultipartBody.Part.createFormData(
                    "avatar",
                    myFile.name,
                    it1
                )
            }
            val dialogBinding = BottomImageReviewBinding.inflate(layoutInflater)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(dialogBinding.root)
            bottomSheetDialog.show()
            with(dialogBinding) {
                ivImageReview.loadImageUser(currentPhotoPath)
                btnSaveAvatar.setOnClickListener{
                    val token = SharedPreference(requireContext()).userToken
                    viewModel.editAvatar(token, imageMultipart)
                    bottomSheetDialog.dismiss()
                }
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activity?.let { intent.resolveActivity(it.packageManager) }

        createCustomTempFile(requireContext()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.qatros.qtn_bina_murid",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
}