package com.qatros.binamurid.ui.parent.childProfile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qatros.binamurid.data.remote.response.Children
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.parent.child.FormChildViewModel
import com.qatros.binamurid.utils.createCustomTempFile
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.utils.requestPermission
import com.qatros.binamurid.utils.uriToFile
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityChildProfileBinding
import com.qatros.binamurid.databinding.BottomAddImageBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File

class ChildProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChildProfileBinding

    private val viewModel : FormChildViewModel by inject()

    private val permissions =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    private var finalFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

    }

    private fun init() {
        val data = intent.getParcelableExtra<Children>(CHILD_DATA)
        with(binding) {
            imgProfileAnak.loadImageUser(data?.avatar)
            txtNamaChild.setText(data?.fullName)
            edAsalSekolah.setText(data?.school)
            edNamaAnak.setText(data?.fullName)
            edTanggalLahirAnak.setText(data?.dateOfBirth)
            btnAddImage.setOnClickListener {
                showBottomSheetDialog()
            }
            apply{
                edAsalSekolah.addTextChangedListener(loginTextWatcher)
                edNamaAnak.addTextChangedListener(loginTextWatcher)
                edTanggalLahirAnak.addTextChangedListener(loginTextWatcher)
            }
            btnEditProfileChild.setOnClickListener {
                val token = SharedPreference(this@ChildProfileActivity).userToken
                val childrenId = data?.childrenId
                val fullName = edNamaAnak.text.toString().toRequestBody("text/plain".toMediaType())
                val school = edAsalSekolah.text.toString().toRequestBody("text/plain".toMediaType())
                val requestImageFile =
                    finalFile?.asRequestBody("image/jpg".toMediaTypeOrNull())
                val imageMultipart = requestImageFile?.let { it1 ->
                    MultipartBody.Part.createFormData(
                        "avatar_children",
                        finalFile?.name,
                        it1
                    )
                }
                viewModel.editChildrenProfile(token, childrenId ?: 0, fullName, school, imageMultipart)
            }
        }
    }

    private val loginTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            binding.apply{
                when {
                    edAsalSekolah.text!!.isEmpty() -> {
                        edAsalSekolah.error = "Name School Required"
                    }
                    edNamaAnak.text!!.isEmpty() -> {
                        edNamaAnak.error = "Name Required"
                    }
                    edTanggalLahirAnak.text!!.isEmpty() -> {
                        edTanggalLahirAnak.error = "Date Required"
                    }
                    else -> {

                    }

                }
                btnEditProfileChild.isEnabled =  edAsalSekolah.text!!.isNotEmpty() && edNamaAnak.text!!.isNotEmpty() && edTanggalLahirAnak.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edAsalSekolah.text?.isBlank()?.not() == true && edNamaAnak.text?.isBlank()?.not() == true && edTanggalLahirAnak.text?.isBlank()?.not() == true) {
                    btnEditProfileChild.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnEditProfileChild.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun showBottomSheetDialog() {
        val dialogBinding = BottomAddImageBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        with(dialogBinding) {
            btnAddCamera.setOnClickListener {
                permissions.forEach {
                    this@ChildProfileActivity.let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, this@ChildProfileActivity
                            )
                            return@setOnClickListener
                        }
                    }
                }
                startTakePhoto()
                bottomSheetDialog.dismiss()
            }

            btnAddGalery.setOnClickListener {
                permissions.forEach {
                    this@ChildProfileActivity.let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, this@ChildProfileActivity
                            )
                            return@setOnClickListener
                        }
                    }
                }
                startGallery()
                bottomSheetDialog.dismiss()
            }
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            finalFile = myFile
            binding.btnEditProfileChild.isEnabled = true
            binding.btnEditProfileChild.setBackgroundColor(resources.getColor(R.color.blue))
            binding.imgProfileAnak.setImageURI(selectedImg)
        }
    }

    private lateinit var currentPhotoPath: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            finalFile = myFile
            binding.btnEditProfileChild.isEnabled = true
            binding.btnEditProfileChild.setBackgroundColor(resources.getColor(R.color.blue))
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imgProfileAnak.setImageBitmap(result)
        }
    }

    companion object {
        val CHILD_DATA = "childProfile.data"
    }
}