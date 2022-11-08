package com.qatros.qtn_bina_murid.ui.pedagogue.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.qatros.qtn_bina_murid.databinding.FragmentHomePedagogueBinding
import com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren.ScanChildrenActivity
import com.qatros.qtn_bina_murid.utils.requestPermission

class HomePedagogueFragment : Fragment() {

    private lateinit var binding: FragmentHomePedagogueBinding

    private val permissions =
        listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomePedagogueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            btnInviteChild.setOnClickListener{
                permissions.forEach {
                    requireActivity().let { ctx ->
                        if (ContextCompat.checkSelfPermission(
                                ctx,
                                it
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            requestPermission(
                                permissions, {}, {}, requireActivity())
                            return@setOnClickListener
                        }
                    }
                }
                startActivity(Intent(activity, ScanChildrenActivity::class.java))
            }
        }
    }
}