package com.qatros.binamurid.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qatros.binamurid.base.BaseFragment
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.utils.*
import com.qatros.binamurid.databinding.BottomAddImageBinding
import com.qatros.binamurid.databinding.BottomImageReviewBinding
import com.qatros.binamurid.databinding.FragmentProfileBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by sharedViewModel()

    private val permissions =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

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
                permissions.forEach {
                    requireActivity().let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, requireActivity())
                            return@setOnClickListener
                        }
                    }
                }
                startTakePhoto()
                bottomSheetDialog.dismiss()
            }

            btnAddGalery.setOnClickListener {
                permissions.forEach {
                    requireActivity().let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, requireActivity())
                            return@setOnClickListener
                        }
                    }
                }
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
            val length = myFile.length()
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
                ivImageReview.loadImageUser(selectedImg.toString())
                btnSaveAvatar.setOnClickListener{
                    if (length <= 1000000) {
                        val token = SharedPreference(requireContext()).userToken
                        viewModel.editAvatar(token, imageMultipart)
                        bottomSheetDialog.dismiss()
                    } else {
                        requireContext().toast("Ukuran Gambar Melebihi 1 Mb")
                        bottomSheetDialog.dismiss()
                    }
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
            val length = myFile.length()
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
                ivImageReview.setImageBitmap(result)
                btnSaveAvatar.setOnClickListener{
                    if (length <= 1000000) {
                        val token = SharedPreference(requireContext()).userToken
                        viewModel.editAvatar(token, imageMultipart)
                        bottomSheetDialog.dismiss()
                    } else {
                        requireContext().toast("Ukuran Gambar Melebihi 1 Mb")
                        bottomSheetDialog.dismiss()
                    }
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
                "com.qatros.binamurid",
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