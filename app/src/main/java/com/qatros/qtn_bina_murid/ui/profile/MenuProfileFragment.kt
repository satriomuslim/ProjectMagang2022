package com.qatros.qtn_bina_murid.ui.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.request.AddRoleRequest
import com.qatros.qtn_bina_murid.databinding.FragmentMenuProfileBinding
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

                        val dialog = Dialog(requireContext())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_change_role_2)
                        dialog.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                        }, 1000)
                    } else {
                        val data = AddRoleRequest(
                            role = "parent"
                        )
                        viewModel.addRoleUser(token, data)

                        val dialog = Dialog(requireContext())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_change_role_2)
                        dialog.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                        }, 1000)
                    }

                }
            } else {
                if (SharedPreference(requireContext()).userRole == 1) {
                    tvChangeUser.text = "Ganti Ke Akun Pedagogue"
                    btnChangeUser.setOnClickListener {
                        SharedPreference(requireContext()).userRole = 2
                        val dialog = Dialog(requireContext())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_change_role)
                        dialog.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                            startActivity(Intent(activity, NavigationPedagogueActivity::class.java))
                            activity?.finish()
                        }, 1000)
                    }
                } else {
                    tvChangeUser.text = "Ganti Ke Akun Parent"
                    btnChangeUser.setOnClickListener {
                        SharedPreference(requireContext()).userRole = 1
                        Toast.makeText(requireActivity(), "Success Change Account", Toast.LENGTH_SHORT).show()
                        val dialog = Dialog(requireContext())
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setContentView(R.layout.popup_change_role)
                        dialog.show()

                        Handler(Looper.getMainLooper()).postDelayed({
                            dialog.dismiss()
                            startActivity(Intent(activity, NavigationParentActivity::class.java))
                            activity?.finish()
                        }, 1000)
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