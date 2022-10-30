package com.qatros.qtn_bina_murid.ui.parent.child

import android.Manifest
import android.R
import android.R.attr.bitmap
import android.R.attr.data
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import com.bumptech.glide.Glide
import com.dicoding.storyapp.data.request.AddStoryRequest
import com.dicoding.storyapp.databinding.ActivityAddStoryBinding
import com.dicoding.storyapp.di.SharedPreference
import com.dicoding.storyapp.utils.createCustomTempFile
import com.dicoding.storyapp.utils.uriToFile
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.qatros.qtn_bina_murid.utils.createCustomTempFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.*


class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding

    private val permissions =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    private val viewModel: AddStoryViewModel by inject()

    private var finalFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeAddSuccess().observe(this) { success ->
            if (success) {
                binding.pbAddStory.isGone = true
                finish()
            }
        }
    }

    private fun init() {
        with(binding) {
            btnToCamera.setOnClickListener {
                permissions.forEach {
                    this@AddStoryActivity.let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {})
                            return@setOnClickListener
                        }
                    }
                }
                startTakePhoto()
            }

            btnToGallery.setOnClickListener {
                permissions.forEach {
                    this@AddStoryActivity.let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {})
                            return@setOnClickListener
                        }
                    }
                }
                startGallery()
            }

            btnSubmitStory.setOnClickListener {
                if (binding.etDescStory.text.toString().isNotBlank() && finalFile != null) {

                    val requestImageFile =
                        finalFile!!.asRequestBody("image/jpg".toMediaTypeOrNull())
                    val imageMultipart = MultipartBody.Part.createFormData(
                        "photo",
                        finalFile!!.name,
                        requestImageFile
                    )

                    val token = SharedPreference(this@AddStoryActivity).getToken("token")

                    viewModel.postStory("Bearer $token",
                        binding.etDescStory.text.toString()
                            .toRequestBody("text/plain".toMediaType()),
                        imageMultipart
                    )

                    pbAddStory.isGone = false

                }
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            finalFile = myFile
            binding.imgPreview.setImageURI(selectedImg)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            finalFile = myFile
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imgPreview.setImageBitmap(result)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.dicoding.storyapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    companion object {
        private const val FILE_TYPE = "image/jpg"
        private const val FILE_PARAM_NAME = "photo"
    }
}