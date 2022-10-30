package com.qatros.qtn_bina_murid.ui.parent.scan

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.qatros.qtn_bina_murid.data.remote.response.Children
import com.qatros.qtn_bina_murid.databinding.ActivityScanBarcodeParentsBinding
import com.qatros.qtn_bina_murid.ui.parent.home.HomeViewModel
import com.qatros.qtn_bina_murid.utils.loadImageUser
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
            generateQR(it)
            with(binding) {
                pbScanBarcodeParents.isGone = true
                binding.tvInviteToken.text = it.toUpperCase(Locale.ROOT)
            }
        }
    }
    private fun init(){
        val child = intent.getParcelableExtra<Children>(CHILD_DATA)
        viewModel.getInviteChildren(child?.childrenId ?: 0)
        with(binding){
            pbScanBarcodeParents.isGone = false
            btnBackFromBarcode.setOnClickListener{
                finish()
            }
            tvNama.text = child?.nickName
            imgUser.loadImageUser(child?.avatar)

        }
    }

    private fun generateQR(text: String) {
        val writer = MultiFormatWriter()
        try {
            val matrix: BitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 600, 600)
            val encoder = BarcodeEncoder()
            val bitmap: Bitmap = encoder.createBitmap(matrix)
            binding.ivBarcode.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

//    private fun generateBarcode() {
//        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
//        val display: Display = manager.defaultDisplay
//
//        val point = Point()
//        display.getSize(point)
//
//        val width: Int = point.x
//        val height: Int = point.y
//
//        var dimen = if (width < height) width else height
//        dimen = dimen * 3 / 4
//
//        qrgEncoder = QRGEncoder(dataEdt.getText().toString(), null, QRGContents.Type.TEXT, dimen)
//        try {
//            bitmap = qrgEncoder.encodeAsBitmap()
//            qrCodeIV.setImageBitmap(bitmap)
//        } catch (e: WriterException) {
//            Log.e("Tag", e.toString())
//        }
//    }

    companion object {
        val CHILD_DATA = "ScanBarcodeParents.childid"
    }
}