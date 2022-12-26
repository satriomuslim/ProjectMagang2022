package com.qatros.binamurid.ui.pedagogue.daily

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qatros.binamurid.data.remote.request.AddReportRequest
import com.qatros.binamurid.data.remote.request.Subject
import com.qatros.binamurid.data.remote.request.SubjectRequest
import com.qatros.binamurid.data.remote.response.Children
import com.qatros.binamurid.di.SharedPreference
import com.qatros.binamurid.ui.parent.daily.DailyReportAdapter
import com.qatros.binamurid.ui.parent.daily.SpinChildAdapter
import com.qatros.binamurid.ui.pedagogue.daily.DateAdapter.onItemClick
import com.qatros.binamurid.utils.InputFilterMinMax
import com.qatros.binamurid.utils.loadImageUser
import com.qatros.binamurid.R
import com.qatros.binamurid.databinding.BottomSheetInputDailyPengagogueBinding
import com.qatros.binamurid.databinding.FragmentDailyPedagogueBinding
import com.qatros.binamurid.databinding.PopupAddSubjectBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*


class DailyPedagogueFragment : Fragment(), onItemClick {

    private lateinit var binding: FragmentDailyPedagogueBinding

    private val dates = ArrayList<Date>()
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val currentDate = Calendar.getInstance(Locale.ENGLISH).time
    private lateinit var dateDefault: String
    private val viewModel: DailyPedagogueViewModel by sharedViewModel()
    private lateinit var token: String
    private var userId: Int = 0
    private var childrenId: Int = 0
    private lateinit var pedagogueName: String
    private var haveChild = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyPedagogueBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeData()
        dateDefault = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
        userId = SharedPreference(requireContext()).userId
        token = SharedPreference(requireContext()).userToken
        pedagogueName = SharedPreference(requireContext()).userName
        viewModel.getChildList(token, "pedagogue")
        binding.pbDailyReportPendagogue.isGone = false
    }

    private fun observeData() {
        with(viewModel) {
            observeGetChildListSuccess().observe(viewLifecycleOwner) {
                if(it?.data.isNullOrEmpty()) {
                    binding.pbDailyReportPendagogue.isGone = true
                    haveChild = false
                    Toast.makeText(
                        requireContext(),
                        "Anak tidak ditemukan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    haveChild = true
                    val adapter = it?.data?.let { it1 ->
                        SpinChildAdapter(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            it1
                        )
                    }
                    binding.spChildPedagogue.adapter = adapter

                    binding.spChildPedagogue.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                adapterView: AdapterView<*>?, view: View?,
                                position: Int, id: Long
                            ) {
                                val child: Children = adapter?.getItem(position) ?: Children()
                                childrenId = child.childrenId
                                binding.circleImageView.loadImageUser(child.avatar)
                                getReportPedagogue(token, dateDefault, childrenId, userId)
                            }

                            override fun onNothingSelected(adapter: AdapterView<*>?) {

                            }
                        }
                }
            }

            observeGetReportPedagogue().observe(viewLifecycleOwner) { data ->
                binding.pbDailyReportPendagogue.isGone = true
                binding.laNotFoundDailyPedagogue.isGone = true
                binding.rvDetailDailyParent.isGone = false
                with(binding.rvDetailDailyParent) {
                    adapter = DailyReportAdapter(data?.data ?: listOf(), pedagogueName)
                    layoutManager = LinearLayoutManager(context)
                }
            }

            observeErrorGetReport().observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { data ->
                    binding.pbDailyReportPendagogue.isGone = true
                    binding.laNotFoundDailyPedagogue.isGone = false
                    binding.rvDetailDailyParent.isGone = true
                }
            }


        }
    }

    private fun init() {
        with(binding) {
            val monthCalendar = cal.clone() as Calendar
            val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
            while (dates.size < maxDaysInMonth) {
                dates.add(monthCalendar.time)
                monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
            }
            btnAllPedagogue.setOnClickListener {
                startActivity(Intent(activity, DailyPedagogueAllActivity::class.java))
            }

            with(rvDateDailyDetailPedagogue) {
                val dateAdapter = DateAdapter(dates, currentDate, this@DailyPedagogueFragment)
                adapter = dateAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }

            btnAddReport.setOnClickListener {
                if (haveChild){
                    showBottomSheetDialog()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Anda Tidak Memiliki Anak Didik",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun showBottomSheetDialog() {

        val dialogBinding = BottomSheetInputDailyPengagogueBinding.inflate(layoutInflater)
        val dialogTextWatcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                dialogBinding.apply {
                    when {
                        edAddNilai.text!!.isEmpty() -> {
                            edAddNilai.error = "Nilai Required"
                        }
                        edDesc.text!!.isEmpty() -> {
                            edDesc.error = "Description Required"
                        }
                        else -> {

                        }

                    }
                    btnRegisterChild.isEnabled =
                        edDesc.text!!.isNotEmpty() && edAddNilai.text!!.isNotEmpty()
                }

            }

            override fun afterTextChanged(s: Editable) {
                dialogBinding.apply {
                    if (edAddNilai.text?.isBlank()
                            ?.not() == true && edDesc.text?.isBlank()
                            ?.not() == true
                    ) {
                        btnRegisterChild.setBackgroundColor(resources.getColor(R.color.blue))
                    } else {
                        btnRegisterChild.setBackgroundColor(resources.getColor(R.color.grey))
                    }
                }
            }

        }
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        with(dialogBinding) {
            btnBackFromInput.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            edAddNilai.filters = (arrayOf<InputFilter>(InputFilterMinMax("1", "100")))
            btnRegisterChild.setOnClickListener {
                val data = AddReportRequest(
                    description = edDesc.text.toString(),
                    score = edAddNilai.text.toString().toInt(),
                    date = dateDefault
                )
                viewModel.postReport(token, childrenId, userId, data)
                pbInputReportPedagogue.isGone = false

                viewModel.observePostReportSuccess().observe(viewLifecycleOwner) {
                    it.getContentIfNotHandled()?.let { success ->
                        if(success) {
                            pbInputReportPedagogue.isGone = true
                            bottomSheetDialog.dismiss()
                            viewModel.getReportPedagogue(token, dateDefault, childrenId, userId)
                        }
                    }
                }
            }
            edAddNilai.addTextChangedListener(dialogTextWatcher)
            edDesc.addTextChangedListener(dialogTextWatcher)
        }

    }

    override fun setItemClick(data: Date, position: Int) {
        dateDefault = SimpleDateFormat("yyyy-MM-dd").format(data.time)
        viewModel.getReportPedagogue(token, dateDefault, childrenId, userId)
        binding.pbDailyReportPendagogue.isGone = false
    }

    override fun onStart() {
        super.onStart()
        val subject = SharedPreference(requireContext()).userSubject
        if (subject == "") {
            val dialogBinding = PopupAddSubjectBinding.inflate(layoutInflater)
            val alertDialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
            val dialogTextWatcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    dialogBinding.apply {
                        when {
                            edSubject.text!!.isEmpty() -> {
                                edSubject.error = "Subject Required"
                            }
                            else -> {

                            }

                        }
                        btnAddSubject.isEnabled =
                            edSubject.text!!.isNotEmpty()
                    }

                }

                override fun afterTextChanged(s: Editable) {
                    dialogBinding.apply {
                        if (edSubject.text?.isBlank()
                                ?.not() == true
                        ) {
                            btnAddSubject.setBackgroundColor(resources.getColor(R.color.blue))
                        } else {
                            btnAddSubject.setBackgroundColor(resources.getColor(R.color.grey))
                        }
                    }
                }

            }
            alertDialog.setCancelable(false)
            val dialog = alertDialog.show()
            with(dialogBinding) {
                edSubject.addTextChangedListener(dialogTextWatcher)
                btnClosePopup.setOnClickListener {
                    viewModel.indicatorDailyreport(true)
                    dialog.dismiss()
                }

                btnAddSubject.setOnClickListener {
                    val tokenSubject = SharedPreference(requireContext()).userToken
                    val data = SubjectRequest(
                        subject = Subject(
                            name = edSubject.text.toString()
                        )
                    )
                    viewModel.postSubject(tokenSubject, data)

                    viewModel.observePostSubjectSuccess().observe(viewLifecycleOwner) {
                        it.getContentIfNotHandled()?.let { success ->
                            if(success) {
                                SharedPreference(requireContext()).userSubject = edSubject.text.toString()
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }
        }
    }
}