package com.qatros.binamurid.ui.pedagogue.scanChildren

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.qatros.binamurid.data.remote.request.InviteChildRequest
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.databinding.ActivityScanChildrenResultBinding
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
                it.getContentIfNotHandled()?.let { success ->
                    if (success) {
                        binding.pbAddChildren.isGone = true
                        this@ScanChildrenResultActivity.toast("Berhasil Mengundang Anak")
                        finish()
                    } else {
                        this@ScanChildrenResultActivity.toast("Error Mengundang Anak")
                    }
                }
            }

            observeConfirmChild().observe(this@ScanChildrenResultActivity) {
                binding.tvConfirmBarcode.text = it?.children?.fullname
                binding.ivConfirmBarcode.loadImageUser(it?.children?.avatar)
                binding.btnConfirmData.setOnClickListener {
                    val inviteChildReq = InviteChildRequest(
                        invitation_token = invToken ?: ""
                    )
                    viewModel.postInviteChild(token, inviteChildReq)
                    binding.pbAddChildren.isGone = false
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