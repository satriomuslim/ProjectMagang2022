package com.qatros.binamurid.ui.parent.scan

import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.qatros.binamurid.data.remote.response.Children
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.parent.home.HomeViewModel
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.databinding.ActivityScanBarcodeParentsBinding
import org.koin.android.ext.android.inject
import java.util.*


class ScanBarcodeParentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBarcodeParentsBinding

    private val viewModel: HomeViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarcodeParentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observeData()
    }

    private fun observeData() {
        viewModel.observeGetChildTokenSuccess().observe(this) {
            if (it != null) {
                generateQR(it)
            }
            with(binding) {
                pbScanBarcodeParents.isGone = true
                binding.tvInviteToken.text = "ID :" + it?.toUpperCase(Locale.ROOT)
            }
        }
    }
    private fun init(){
        val child = intent.getParcelableExtra<Children>(CHILD_DATA)
        val token = SharedPreference(this).userToken
        viewModel.getInviteChildren(token, child?.childrenId ?: 0)
        with(binding){
            pbScanBarcodeParents.isGone = false
            btnBackFromBarcode.setOnClickListener{
                finish()
            }
            tvNama.text = child?.fullName
            imgUser.loadImageUser(child?.avatar)

        }
    }

    private fun generateQR(text: String) {
        val writer = MultiFormatWriter()
        try {
            val matrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 800, 800)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(matrix)
            binding.ivBarcode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    companion object {
        val CHILD_DATA = "ScanBarcodeParents.childid"
    }
}