package com.qatros.qtn_bina_murid.ui.pedagogue.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qatros.qtn_bina_murid.databinding.FragmentHomePedagogueBinding
import com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren.ScanChildrenActivity

class HomePedagogueFragment : Fragment() {

    private lateinit var binding: FragmentHomePedagogueBinding

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
                startActivity(Intent(activity, ScanChildrenActivity::class.java))
            }
        }
    }
}