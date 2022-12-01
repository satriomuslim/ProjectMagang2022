package com.qatros.qtn_bina_murid.ui.parent.childProfile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.Children
import com.qatros.qtn_bina_murid.databinding.ActivityChildProfileBinding

class ChildProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChildProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChildProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply{
            edAsalSekolah.addTextChangedListener(loginTextWatcher)
            edNamaAnak.addTextChangedListener(loginTextWatcher)
            edNamaPanggilanAnak.addTextChangedListener(loginTextWatcher)
            edTanggalLahirAnak.addTextChangedListener(loginTextWatcher)
        }
        init()

    }

    private fun init() {
        val data = intent.getParcelableExtra<Children>(CHILD_DATA)
        with(binding) {
            edAsalSekolah.setText(data?.school)
            edNamaAnak.setText(data?.fullName)
            edNamaPanggilanAnak.setText(data?.nickName)
            edTanggalLahirAnak.setText(data?.dateOfBirth)
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
                btnEditProfileChild.isEnabled =  edAsalSekolah.text!!.isNotEmpty() && edNamaAnak.text!!.isNotEmpty() && edNamaPanggilanAnak.text!!.isNotEmpty() && edTanggalLahirAnak.text!!.isNotEmpty()

            }

        }

        override fun afterTextChanged(s: Editable) {
            binding.apply {
                if (edAsalSekolah.text?.isBlank()?.not() == true && edNamaAnak.text?.isBlank()?.not() == true && edNamaPanggilanAnak.text?.isBlank()?.not() == true && edTanggalLahirAnak.text?.isBlank()?.not() == true) {
                    btnEditProfileChild.setBackgroundColor(resources.getColor(R.color.blue))
                } else {
                    btnEditProfileChild.setBackgroundColor(resources.getColor(R.color.grey))
                }
            }
        }

    }

    companion object {
        val CHILD_DATA = "childProfile.data"
    }
}