package com.qatros.qtn_bina_murid.ui.parent.child

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.R

import com.qatros.qtn_bina_murid.databinding.ActivityFormChildBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.android.ext.android.inject

class FormChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormChildBinding

    private val viewModel: FormChildViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            edAsalSekolah.addTextChangedListener(loginTextWatcher)
            edNamaAnak.addTextChangedListener(loginTextWatcher)
            edNamaPanggilanAnak.addTextChangedListener(loginTextWatcher)
            edTanggalLahirAnak.addTextChangedListener(loginTextWatcher)
        }

        observeData()
        init()
    }

    private fun observeData() {
        viewModel.observeAddChildSuccess().observe(this) {
            Toast.makeText(this, "SUCCESS MENAMBAH ANAK", Toast.LENGTH_SHORT).show()
            finish()
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
                    edNamaPanggilanAnak.text!!.isEmpty() -> {
                        edNamaPanggilanAnak.error = "Nickname Required"
                    }
                    edTanggalLahirAnak.text!!.isEmpty() -> {
                        edTanggalLahirAnak.error = "Date Required"
                    }
                    else -> {

                    }

                }
                btnRegisterChild.isEnabled =  edAsalSekolah.text!!.isNotEmpty() && edNamaAnak.text!!.isNotEmpty() && edNamaPanggilanAnak.text!!.isNotEmpty() && edTanggalLahirAnak.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edAsalSekolah.text?.isBlank()?.not() == true && edNamaAnak.text?.isBlank()?.not() == true && edNamaPanggilanAnak.text?.isBlank()?.not() == true && edTanggalLahirAnak.text?.isBlank()?.not() == true) {
                    btnRegisterChild.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnRegisterChild.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    private fun init() {
        with(binding) {
            btnRegisterChild.setOnClickListener{
                val token = SharedPreference(this@FormChildActivity).userToken
                val fullName = edNamaAnak.text.toString().toRequestBody("text/plain".toMediaType())
                val nickName = edNamaPanggilanAnak.text.toString().toRequestBody("text/plain".toMediaType())
                val school = edAsalSekolah.text.toString().toRequestBody("text/plain".toMediaType())
                val birthOfDate = edTanggalLahirAnak.text.toString().toRequestBody("text/plain".toMediaType())
                val image: MultipartBody.Part? = null
                if (image != null) {
                    viewModel.postAddChild(token, fullName, nickName, school, birthOfDate, image)
                }
            }
        }
    }

}