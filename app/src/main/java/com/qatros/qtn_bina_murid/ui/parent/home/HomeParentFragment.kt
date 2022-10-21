package com.qatros.qtn_bina_murid.ui.parent.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.databinding.FragmentHomeParentBinding
import com.qatros.qtn_bina_murid.ui.parent.child.FormChildActivity

class HomeParentFragment : Fragment() {

    private lateinit var binding: FragmentHomeParentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeParentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        with(binding) {
            with(rvDailyUpdate){
                adapter = HomeParentAdapter(listOf("", "", "", "", "", ""))
                layoutManager = LinearLayoutManager(requireContext())
            }

            btnInvitePendagogue.setOnClickListener{
                startActivity(Intent(activity, HomeScanListActivity::class.java))
            }

            wrapButtonDaftarAnak.setOnClickListener{
                startActivity(Intent(activity, FormChildActivity::class.java))
            }
        }

    }
}