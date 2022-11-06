package com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.data.remote.request.InviteChildRequest
import com.qatros.qtn_bina_murid.databinding.ActivityScanChildrenBinding
import com.qatros.qtn_bina_murid.databinding.ActivityScanChildrenResultBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.utils.loadImageUser
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.android.ext.android.inject

class ScanChildrenResultActivity : AppCompatActivity() {

    private val viewModel: ScanChildrenViewModel by inject()

    private lateinit var binding: ActivityScanChildrenResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanChildrenResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = intent.getStringExtra(CHILD_DATA)
        val token = SharedPreference(this).userToken
        val segments = data?.split(",".toRegex())?.toTypedArray()
        val invToken = segments?.get(0)
        val name = segments?.get(1)
        val avatar = segments?.get(2)
        binding.tvConfirmBarcode.text = name
        binding.ivConfirmBarcode.loadImageUser(avatar)
        binding.btnConfirmData.setOnClickListener {
            val inviteChildReq = InviteChildRequest(
                invitation_token = invToken ?: ""
            )
            viewModel.postInviteChild(token, inviteChildReq)
        }

        binding.btnDeniedData.setOnClickListener {
            this.toast("OK")
        }

        observeData()
    }

    private fun observeData() {
        viewModel.observeInviteChildSuccess().observe(this) {
            this.toast("Berhasil Mengundang Anak")
        }
    }

    companion object {
        val CHILD_DATA = "ScanChildrenResult.invtoken"
    }
}