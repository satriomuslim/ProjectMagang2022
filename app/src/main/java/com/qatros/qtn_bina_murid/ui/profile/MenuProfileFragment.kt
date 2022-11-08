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
import com.qatros.qtn_bina_murid.data.remote.request.AddRoleRequest
import com.qatros.qtn_bina_murid.databinding.FragmentMenuProfileBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.pedagogue.navigation.NavigationPedagogueActivity
import org.koin.android.ext.android.inject


class MenuProfileFragment : Fragment() {

    private lateinit var binding: FragmentMenuProfileBinding

    private val viewModel: ProfileViewModel by inject()

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
        observeData()
    }

    private fun observeData() {
        viewModel.observeAddRoleSuccess().observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                SharedPreference(requireContext()).userListRole = data.data.role.toMutableSet()
                binding.tvChangeUser.text = "Ganti Akun"
            }
        }
    }

    private fun init() {
        with(binding) {
            Log.e("TAG", "init: ${SharedPreference(requireContext()).userListRole?.size}")
            if(SharedPreference(requireContext()).userListRole?.size == 1) {
                tvChangeUser.text = "Daftar Pedagogue"
                val token = SharedPreference(requireContext()).userToken
                btnChangeUser.setOnClickListener {
                    if (SharedPreference(requireContext()).userRole == 1) {
                        val data = AddRoleRequest(
                            role = "pedagogue"
                        )
                        viewModel.addRoleUser(token, data)
                    } else {
                        val data = AddRoleRequest(
                            role = "parent"
                        )
                        viewModel.addRoleUser(token, data)
                    }

                }
            } else {
                tvChangeUser.text = "Ganti Akun"
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
            }

            btnEditProfile.setOnClickListener{
                findNavController().navigate(R.id.action_menuProfile_to_editProfileFragment)
            }

            btnLogout.setOnClickListener {
                SharedPreference(requireContext()).resetSharedPref()
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}