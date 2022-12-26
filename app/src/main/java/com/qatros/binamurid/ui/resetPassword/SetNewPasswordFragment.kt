package com.qatros.binamurid.ui.resetPassword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.qatros.binamurid.ui.login.LoginActivity
import com.qatros.binamurid.databinding.FragmentSetNewPasswordBinding

class SetNewPasswordFragment : Fragment() {
    private lateinit var binding: FragmentSetNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSetNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            btnReset.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}