package com.qatros.qtn_bina_murid.ui.parent.child

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.qatros.qtn_bina_murid.databinding.ActivityFormChildBinding

class FormChildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormChildBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFormChildBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}