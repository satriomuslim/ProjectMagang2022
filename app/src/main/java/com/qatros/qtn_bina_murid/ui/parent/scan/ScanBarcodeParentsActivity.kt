package com.qatros.qtn_bina_murid.ui.parent.scan

import android.R.attr.bitmap
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.databinding.ActivityScanBarcodeParentsBinding


class ScanBarcodeParentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBarcodeParentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarcodeParentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        with(binding){
            btnBackFromBarcode.setOnClickListener{
                finish()
            }
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
}