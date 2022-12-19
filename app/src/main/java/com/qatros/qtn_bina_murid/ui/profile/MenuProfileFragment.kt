package com.qatros.qtn_bina_murid.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.AddRoleRequest
import com.qatros.qtn_bina_murid.databinding.FragmentMenuProfileBinding
import com.qatros.qtn_bina_murid.databinding.PopupChangeRole2Binding
import com.qatros.qtn_bina_murid.databinding.PopupChangeRoleBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.login.LoginActivity
import com.qatros.qtn_bina_murid.ui.parent.navigation.NavigationParentActivity
import com.qatros.qtn_bina_murid.ui.pedagogue.navigation.NavigationPedagogueActivity
import com.qatros.qtn_bina_murid.utils.toast
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
                context?.toast("Daftar User Baru Success")

            }
        }
    }

    private fun init() {
        with(binding) {
            if (SharedPreference(requireContext()).userListRole?.size == 1) {
                if (SharedPreference(requireContext()).userRole == 1) {
                    tvChangeUser.text = "Daftar Pedagogue"
                } else {
                    tvChangeUser.text = "Daftar Orang Tua"
                }
                val token = SharedPreference(requireContext()).userToken
                btnChangeUser.setOnClickListener {
                    if (SharedPreference(requireContext()).userRole == 1) {
                        val data = AddRoleRequest(
                            role = "pedagogue"
                        )
                        viewModel.addRoleUser(token, data)

                        val dialogBinding = PopupChangeRole2Binding.inflate(layoutInflater)
                        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)

                        alertDialog.setCancelable(false)
                        val dialog = alertDialog.show()

                        with(dialogBinding) {
                            btnNo.setOnClickListener {
                                dialog.dismiss()
                            }
                            btnYes.setOnClickListener {
                                startActivity(Intent(activity, NavigationPedagogueActivity::class.java))
                                activity?.finish()
                            }
                        }

                    } else {
                        val data = AddRoleRequest(
                            role = "parent"
                        )
                        viewModel.addRoleUser(token, data)

                        val dialogBinding = PopupChangeRole2Binding.inflate(layoutInflater)
                        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)

                        alertDialog.setCancelable(false)
                        val dialog = alertDialog.show()

                        with(dialogBinding) {
                            btnNo.setOnClickListener {
                                dialog.dismiss()
                            }
                            btnYes.setOnClickListener {
                                startActivity(Intent(activity, NavigationParentActivity::class.java))
                                activity?.finish()
                            }
                        }
                    }

                }
            } else {
                if (SharedPreference(requireContext()).userRole == 1) {
                    tvChangeUser.text = "Ganti Ke Akun Pedagogue"
                    btnChangeUser.setOnClickListener {
                        SharedPreference(requireContext()).userRole = 2

                        val dialogBinding = PopupChangeRoleBinding.inflate(layoutInflater)
                        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)

                        alertDialog.setCancelable(false)
                        val dialog = alertDialog.show()

                        with(dialogBinding) {
                            btnNo.setOnClickListener {
                                dialog.dismiss()
                            }
                            btnYes.setOnClickListener {
                                startActivity(Intent(activity, NavigationPedagogueActivity::class.java))
                                activity?.finish()
                            }
                        }
                    }
                } else {
                    tvChangeUser.text = "Ganti Ke Akun Parent"
                    btnChangeUser.setOnClickListener {
                        SharedPreference(requireContext()).userRole = 1

                        val dialogBinding = PopupChangeRoleBinding.inflate(layoutInflater)
                        val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)

                        alertDialog.setCancelable(false)
                        val dialog = alertDialog.show()

                        with(dialogBinding) {
                            btnNo.setOnClickListener {
                                dialog.dismiss()
                            }
                            btnYes.setOnClickListener {
                                startActivity(Intent(activity, NavigationParentActivity::class.java))
                                activity?.finish()
                            }
                        }
                    }
                }
            }

            btnEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_menuProfile_to_editProfileFragment)
            }

            btnEditPassword.setOnClickListener {
                findNavController().navigate(R.id.action_menuProfile_to_changePasswordFragment)
            }

            btnLogout.setOnClickListener {
                SharedPreference(requireContext()).resetSharedPref()
                startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}