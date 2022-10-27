package com.qatros.qtn_bina_murid.ui.parent.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityHomeScanListBinding
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
}