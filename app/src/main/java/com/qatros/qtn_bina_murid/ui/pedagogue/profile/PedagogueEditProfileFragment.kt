package com.qatros.qtn_bina_murid.ui.pedagogue.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.FragmentPendagogueEditProfileBinding

class PedagogueEditProfileFragment : Fragment() {

    private lateinit var binding : FragmentPendagogueEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.apply{
            edNamaLengkap.addTextChangedListener(loginTextWatcher)
            edAlamat.addTextChangedListener(loginTextWatcher)
            edNomorHandphone.addTextChangedListener(loginTextWatcher)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPendagogueEditProfileBinding.inflate(layoutInflater)
        return binding.root
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
}