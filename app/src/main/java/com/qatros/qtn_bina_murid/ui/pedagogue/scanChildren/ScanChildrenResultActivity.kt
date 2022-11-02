package com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.data.remote.request.InviteChildRequest
import com.qatros.qtn_bina_murid.databinding.ActivityScanChildrenBinding
import com.qatros.qtn_bina_murid.databinding.ActivityScanChildrenResultBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import org.koin.android.ext.android.inject

class ScanChildrenResultActivity : AppCompatActivity() {

    private val viewModel: ScanChildrenViewModel by inject()

    private lateinit var binding: ActivityScanChildrenResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanChildrenResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val invToken = intent.getStringExtra(INV_TOKEN)
        val token = SharedPreference(this).userToken
        val inviteChildReq = InviteChildRequest(
            invitation_token = invToken ?: ""
        )
        viewModel.postInviteChild(token, inviteChildReq)
        observeData()
    }

    private fun observeData() {
        viewModel.observeInviteChildSuccess().observe(this) {
        }
    }

    companion object {
        val INV_TOKEN = "ScanChildrenResult.invtoken"
    }
}