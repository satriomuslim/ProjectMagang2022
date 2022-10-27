package com.qatros.qtn_bina_murid.ui.parent.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.databinding.ActivityHomeScanListBinding
import com.qatros.qtn_bina_murid.ui.parent.scan.ScanBarcodeParentsActivity

class HomeScanListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScanListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScanListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init()
    }

    private fun init(){
        with(binding){

            btnChoose.setOnClickListener{
                startActivity(Intent(this@HomeScanListActivity, ScanBarcodeParentsActivity::class.java))
            }

            btnBackFromListchild.setOnClickListener{
                finish()
            }

            with(rvMain){
                adapter = HomeScanListAdapter(listOf("", "", "", "", "", ""))
                layoutManager = LinearLayoutManager(this@HomeScanListActivity)
            }
        }
    }

}