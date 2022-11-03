package com.qatros.qtn_bina_murid.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.base.BaseFragment
import com.qatros.qtn_bina_murid.databinding.FragmentProfileBinding
import com.qatros.qtn_bina_murid.di.SharedPreference

class ProfileFragment : BaseFragment() {
    private lateinit var binding: FragmentProfileBinding
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
    }

    fun settingGoneButton(visible: Boolean) {
        binding.circleImageView.isGone = visible
    }

    private fun init() {
        with(binding) {
            txtNameParent.text = SharedPreference(requireContext()).userName
            txtEmailParent.text = SharedPreference(requireContext()).userEmail
        }
    }
}