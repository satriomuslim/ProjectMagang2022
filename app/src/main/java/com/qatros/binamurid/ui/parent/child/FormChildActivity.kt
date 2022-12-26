package com.qatros.binamurid.ui.parent.child

import android.Manifest
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.utils.createCustomTempFile
import com.qatros.binamurid.utils.requestPermission
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.utils.uriToFile
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityFormChildBinding
import com.qatros.binamurid.databinding.BottomAddImageBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class FormChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormChildBinding

    private val viewModel: FormChildViewModel by inject()

    private val permissions =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    private var finalFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            edAsalSekolah.addTextChangedListener(loginTextWatcher)
            edNamaAnak.addTextChangedListener(loginTextWatcher)
            edTanggalLahirAnak.addTextChangedListener(loginTextWatcher)
        }

        observeData()
        init()
    }

    private fun observeData() {
        with(viewModel) {
            observeAddChildSuccess().observe(this@FormChildActivity) {
                it.getContentIfNotHandled()?.let {
                    binding.pbRegisterChild.isGone = true
                    Toast.makeText(
                        this@FormChildActivity,
                        "SUCCESS MENAMBAH ANAK",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    finish()
                }
            }

            observeError().observe(this@FormChildActivity) {
                it.getContentIfNotHandled()?.let {
                    binding.pbRegisterChild.isGone = true
                    Toast.makeText(this@FormChildActivity, it, Toast.LENGTH_SHORT).show()
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
                    edAsalSekolah.text!!.isEmpty() -> {
                        edAsalSekolah.error = "Name School Required"
                    }
                    edNamaAnak.text!!.isEmpty() -> {
                        edNamaAnak.error = "Name Required"
                    }
                    edTanggalLahirAnak.isClickable.not() -> {
                        edTanggalLahirAnak.error = "Date Required"
                    }
                    else -> {

                    }

                }
                btnRegisterChild.isEnabled =
                    edAsalSekolah.text!!.isNotEmpty() && edNamaAnak.text!!.isNotEmpty() && edTanggalLahirAnak.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edAsalSekolah.text?.isBlank()?.not() == true && edNamaAnak.text?.isBlank()?.not() == true && edTanggalLahirAnak.text?.isBlank()?.not() == true
                ) {
                    btnRegisterChild.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnRegisterChild.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            edTanggalLahirAnak.setOnClickListener {
                getDate()
            }
            btnRegisterChild.setOnClickListener {
                val token = SharedPreference(this@FormChildActivity).userToken
                val fullName = edNamaAnak.text.toString().toRequestBody("text/plain".toMediaType())
                val school = edAsalSekolah.text.toString().toRequestBody("text/plain".toMediaType())
                val birthOfDate =
                    edTanggalLahirAnak.text.toString().toRequestBody("text/plain".toMediaType())
                val requestImageFile =
                    finalFile?.asRequestBody("image/jpg".toMediaTypeOrNull())
                val imageMultipart = requestImageFile?.let { it1 ->
                    MultipartBody.Part.createFormData(
                        "avatar_children",
                        finalFile?.name,
                        it1
                    )
                }

                if (imageMultipart != null) {
                    val length = finalFile?.length() ?: 0
                    if (length <= 1000000) {
                        pbRegisterChild.isGone = false
                        viewModel.postAddChild(
                            token,
                            fullName,
                            school,
                            birthOfDate,
                            imageMultipart
                        )
                    } else {
                        this@FormChildActivity.toast("Ukuran Gambar Melebihi 1 Mb")
                    }
                } else {
                    this@FormChildActivity.toast("Gambar Masih Kosong")
                }

            }

            btnAddImage.setOnClickListener {
                showBottomSheetDialog()
            }
        }
    }

    private fun getDate() {
        val calendar = Calendar.getInstance()
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var month = calendar.get(Calendar.MONTH)
        var year = calendar.get(Calendar.YEAR)
        val dateTime = Calendar.getInstance()
        DatePickerDialog(
            this, R.style.DialogTheme,
            { view, year, month, day ->
                dateTime.set(year, month, day)
                val dateFormater = SimpleDateFormat("dd/MM/yyyy").format(dateTime.time)
                binding.edTanggalLahirAnak.setText(dateFormater)
            },
            year, month, day
        ).show()

    }


    private fun showBottomSheetDialog() {
        val dialogBinding = BottomAddImageBinding.inflate(layoutInflater)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        with(dialogBinding) {
            btnAddCamera.setOnClickListener {
                permissions.forEach {
                    this@FormChildActivity.let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, this@FormChildActivity
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
                    this@FormChildActivity.let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, this@FormChildActivity
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this)
            finalFile = myFile
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
            val result = BitmapFactory.decodeFile(myFile.path)
            binding.imgProfileAnak.setImageBitmap(result)
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

}