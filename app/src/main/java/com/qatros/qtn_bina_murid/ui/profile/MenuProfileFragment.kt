package com.qatros.qtn_bina_murid.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.FragmentMenuProfileBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.pedagogue.navigation.NavigationPedagogueActivity


class MenuProfileFragment : Fragment() {

    private lateinit var binding: FragmentMenuProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            btnChangeUser.setOnClickListener{
                if (SharedPreference(requireContext()).userRole == 1) {
                    SharedPreference(requireContext()).userRole = 2
                    startActivity(Intent(activity, NavigationPedagogueActivity::class.java))
                    activity?.finish()
                } else {
                    SharedPreference(requireContext()).userRole = 1
                    startActivity(Intent(activity, NavigationParentActivity::class.java))
                    activity?.finish()
                }
            }

            btnEditProfile.setOnClickListener{
                findNavController().navigate(R.id.action_menuProfile_to_editProfileFragment)
            }
        }
    }
}