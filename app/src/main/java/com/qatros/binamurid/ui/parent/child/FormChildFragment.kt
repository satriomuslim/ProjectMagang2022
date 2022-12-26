package com.qatros.binamurid.ui.parent.child

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.FragmentFormChildBinding


class FormChildFragment : Fragment() {

    private lateinit var binding : FragmentFormChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply{
            edAsalSekolah.addTextChangedListener(loginTextWatcher)
            edNamaAnak.addTextChangedListener(loginTextWatcher)
            edNamaPanggilanAnak.addTextChangedListener(loginTextWatcher)
            edTanggalLahirAnak.addTextChangedListener(loginTextWatcher)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormChildBinding.inflate(layoutInflater)
        return binding.root
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

}