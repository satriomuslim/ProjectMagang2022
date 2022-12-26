package com.qatros.binamurid.ui.pedagogue.scanChildren

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.ActivityScanChildrenBinding
import com.qatros.binamurid.databinding.PopupInviteIdPendagogueBinding
import org.koin.android.ext.android.inject

class ScanChildrenActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner

    private val viewModel: ScanChildrenViewModel by inject()

    private lateinit var binding: ActivityScanChildrenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanChildrenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        codeScanner = CodeScanner(this, binding.scannerView)

        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, ScanChildrenResultActivity::class.java).putExtra(ScanChildrenResultActivity.CHILD_DATA, it.text))
                finish()
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        codeScanner.startPreview()

        binding.tvInviteToken.setOnClickListener {
            val dialogBinding = PopupInviteIdPendagogueBinding.inflate(layoutInflater)
            val alertDialog = AlertDialog.Builder(this).setView(dialogBinding.root)
            val dialogTextWatcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    dialogBinding.apply {
                        when {
                            edId.text!!.isEmpty() -> {
                                edId.error = "ID Required"
                            }
                            else -> {

                            }

                        }
                        btnInviteChild.isEnabled =
                            edId.text!!.isNotEmpty()
                    }

                }

                override fun afterTextChanged(s: Editable) {
                    dialogBinding.apply {
                        if (edId.text?.isBlank()
                                ?.not() == true
                        ) {
                            btnInviteChild.setBackgroundColor(resources.getColor(R.color.blue))
                        } else {
                            btnInviteChild.setBackgroundColor(resources.getColor(R.color.grey))
                        }
                    }
                }

            }
            val dialog = alertDialog.show()
            with(dialogBinding) {
                edId.addTextChangedListener(dialogTextWatcher)
                btnClosePopupId.setOnClickListener {
                    dialog.dismiss()
                }

                btnInviteChild.setOnClickListener {
                    dialog.dismiss()
                    startActivity(Intent(this@ScanChildrenActivity, ScanChildrenResultActivity::class.java).putExtra(ScanChildrenResultActivity.CHILD_DATA, edId.text.toString()))
                    finish()
                }
            }
        }
    }
}