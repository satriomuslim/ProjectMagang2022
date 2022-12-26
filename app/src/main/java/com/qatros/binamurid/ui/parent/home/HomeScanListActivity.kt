package com.qatros.binamurid.ui.parent.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.qatros.binamurid.data.remote.response.Children
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.parent.scan.ScanBarcodeParentsActivity
import com.qatros.binamurid.utils.toast
import com.qatros.binamurid.databinding.ActivityHomeScanListBinding
import org.koin.android.ext.android.inject

class HomeScanListActivity : AppCompatActivity(), HomeScanListAdapter.onItemClick {

    private lateinit var binding: ActivityHomeScanListBinding

    private val viewModel: HomeViewModel by inject()

    private var childrenData: Children? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScanListBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init()
        observeData()
        val token = SharedPreference(this).userToken
        viewModel.getChildList(token, "parent")
    }

    private fun observeData() {
        viewModel.observeGetChildListSuccess().observe(this) {
            Log.e("TAG", "observeData: ${it?.data}", )
            if(it?.data == null) {
                with(binding) {
                    laNotFoundScanList.isGone = false
                    rvMain.isGone = true
                }
            }
            with(binding.rvMain){
                adapter = it?.data?.let { it1 -> HomeScanListAdapter(it1, this@HomeScanListActivity) }
                layoutManager = LinearLayoutManager(this@HomeScanListActivity)
            }
        }
        viewModel.observeError().observe(this) {
            with(binding) {
                laNotFoundScanList.isGone = false
                rvMain.isGone = true
            }
        }
    }

    private fun init(){
        with(binding){

            btnChoose.setOnClickListener{
                if (childrenData != null) {
                    startActivity(Intent(this@HomeScanListActivity, ScanBarcodeParentsActivity::class.java).putExtra(ScanBarcodeParentsActivity.CHILD_DATA, childrenData))
                } else {
                    this@HomeScanListActivity.toast("Silahkan Pilih Anak")
                }
            }

            btnBackFromListchild.setOnClickListener{
                finish()
            }
        }
    }

    override fun setItemClick(data: Children, position: Int) {
        Log.e("TAG", "setItemClick: $data", )
        childrenData = data
    }

}