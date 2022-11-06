package com.qatros.qtn_bina_murid.ui.pedagogue.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.FragmentDailyPedagogueBinding

class DailyPedagogueFragment : Fragment() {

    private lateinit var binding: FragmentDailyPedagogueBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyPedagogueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}