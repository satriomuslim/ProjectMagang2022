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

    private lateinit var invToken : String

    private lateinit var token : String

    private lateinit var binding: ActivityScanChildrenResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanChildrenResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        invToken = intent.getStringExtra(CHILD_DATA) ?: ""
        token = SharedPreference(this).userToken
        val dataReq = InviteChildRequest(
            invitation_token = invToken ?: ""
        )
        viewModel.confirmProfileChild(token, dataReq)

        observeData()
    }

    private fun observeData() {
        with(viewModel){
            observeInviteChildSuccess().observe(this@ScanChildrenResultActivity) {
                this@ScanChildrenResultActivity.toast("Berhasil Mengundang Anak")
                finish()
            }

            observeConfirmChild().observe(this@ScanChildrenResultActivity) {
                binding.tvConfirmBarcode.text = it?.children?.fullname
                binding.ivConfirmBarcode.loadImageUser(it?.children?.avatar)
                binding.btnConfirmData.setOnClickListener {
                    val inviteChildReq = InviteChildRequest(
                        invitation_token = invToken ?: ""
                    )
                    viewModel.postInviteChild(token, inviteChildReq)
                }

                binding.btnDeniedData.setOnClickListener {
                    finish()
                }
            }
        }


    }

    companion object {
        val CHILD_DATA = "ScanChildrenResult.invtoken"
    }
}