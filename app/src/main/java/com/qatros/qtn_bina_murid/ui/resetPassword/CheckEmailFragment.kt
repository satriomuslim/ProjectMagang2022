package com.qatros.qtn_bina_murid.ui.resetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.FragmentCheckEmailBinding

class CheckEmailFragment : Fragment() {
    private lateinit var binding: FragmentCheckEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckEmailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            btnSendEmail.setOnClickListener {
                findNavController().navigate(R.id.action_checkEmailFragment_to_setNewPasswordFragment)
            }
        }
    }
}