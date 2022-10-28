package com.qatros.qtn_bina_murid.ui.parent.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.qtn_bina_murid.R
import com.qatros.qtn_bina_murid.data.remote.response.Children
import com.qatros.qtn_bina_murid.databinding.ActivityHomeScanListBinding
import com.qatros.qtn_bina_murid.di.SharedPreference
import com.qatros.qtn_bina_murid.ui.parent.scan.ScanBarcodeParentsActivity
import com.qatros.qtn_bina_murid.utils.toast
import org.koin.android.ext.android.inject

class HomeScanListActivity : AppCompatActivity(), HomeScanListAdapter.onItemClick {

    private lateinit var binding: ActivityHomeScanListBinding

    private val viewModel: HomeViewModel by inject()

    private var idChildren: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScanListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init()
        observeData()
        val token = SharedPreference(this).userToken
        viewModel.getChildList(token)
    }

    private fun observeData() {
        viewModel.observeGetChildListSuccess().observe(this) {
            with(binding.rvMain){
//                adapter = it?.data?.let { it1 -> HomeScanListAdapter(it1, this@HomeScanListActivity) }
                adapter = HomeScanListAdapter(listOf("1", "2", "3", "4", "5", "6","7", "8", "9", "0", "11", "12"), this@HomeScanListActivity)
                layoutManager = LinearLayoutManager(this@HomeScanListActivity)
            }
        }
    }

    private fun init(){
        with(binding){

            btnChoose.setOnClickListener{
                if (idChildren != null) {

                } else {
                    this@HomeScanListActivity.toast("Silahkan Pilih Anak")
                }
                startActivity(Intent(this@HomeScanListActivity, ScanBarcodeParentsActivity::class.java))
            }

            btnBackFromListchild.setOnClickListener{
                finish()
            }
        }
    }

    override fun setItemClick(data: String, position: Int) {
        Log.e("TAG", "setItemClick: $data", )
    }

}