package com.qatros.qtn_bina_murid.ui.parent.child

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        observeData()
        init()
    }

    private fun observeData() {
        viewModel.observeAddChildSuccess().observe(this) {
            Toast.makeText(this, "SUCCESS MENAMBAH ANAK", Toast.LENGTH_SHORT).show()
            finish()
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