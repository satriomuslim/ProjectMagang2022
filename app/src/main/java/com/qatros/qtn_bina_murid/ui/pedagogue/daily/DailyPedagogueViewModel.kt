package com.qatros.qtn_bina_murid.ui.pedagogue.daily

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.AddReportRequest
import com.qatros.qtn_bina_murid.data.remote.request.SubjectRequest
import com.qatros.qtn_bina_murid.data.remote.response.ChildrenReportResponse
import com.qatros.qtn_bina_murid.data.remote.response.ListChildResponse
import com.qatros.qtn_bina_murid.data.remote.response.ReportResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import io.reactivex.Single
import kotlinx.coroutines.launch

class DailyPedagogueViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val postSubjectSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observePostSubjectSuccess() : LiveData<SingleLiveEvent<Boolean>> = postSubjectSuccess

    private val postReportSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observePostReportSuccess() : LiveData<SingleLiveEvent<Boolean>> = postReportSuccess

    private val getChildListSuccess = MutableLiveData<ListChildResponse?>()
    fun observeGetChildListSuccess() : LiveData<ListChildResponse?> = getChildListSuccess

    private val getReportPedagogue = MutableLiveData<ReportResponse?>()
    fun observeGetReportPedagogue() : LiveData<ReportResponse?> = getReportPedagogue

    private val getAllReportPedagogue = MutableLiveData<ChildrenReportResponse?>()
    fun observeGetAllReportPedagogue() : LiveData<ChildrenReportResponse?> = getAllReportPedagogue

    private val indicatorCurrent = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeIndicatorCurrent() : LiveData<SingleLiveEvent<Boolean>> = indicatorCurrent

    private val isErrorGetReport = MutableLiveData<SingleLiveEvent<Int>>()
    fun observeErrorGetReport() : LiveData<SingleLiveEvent<Int>> = isErrorGetReport

    fun postSubject(token: String, subjectRequest: SubjectRequest) {
        viewModelScope.launch {
            when(val result = repository.postSubject(token, subjectRequest)) {
                is ResponseResult.Success -> {
                    postSubjectSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun postReport(token: String, childrenId: Int, userId: Int, addReportRequest: AddReportRequest) {
        viewModelScope.launch {
            when(val result = repository.postReport(token, childrenId, userId, addReportRequest)) {
                is ResponseResult.Success -> {
                    postReportSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun getChildList(token: String, type: String) {
        viewModelScope.launch {
            when(val result = repository.getListChild(token, type)) {
                is ResponseResult.Success -> {
                    getChildListSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun getReportPedagogue(token: String, date: String, childrenId: Int, userId: Int) {
        viewModelScope.launch {
            when(val result = repository.getReport(token, date, childrenId, userId)) {
                is ResponseResult.Success -> {
                    getReportPedagogue.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isErrorGetReport.postValue(SingleLiveEvent(result.code))
                }
            }
        }
    }

    fun indicatorDailyreport(indicator: Boolean) {
        viewModelScope.launch {
            Log.e("TAG", "indicatorDailyreport: ", )
            indicatorCurrent.postValue(SingleLiveEvent(indicator))
        }
    }

    fun getAllReportPedagogue(token: String) {
        viewModelScope.launch {
            when(val result = repository.getAllReportPedagogue(token)) {
                is ResponseResult.Success -> {
                    getAllReportPedagogue.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isErrorGetReport.postValue(SingleLiveEvent(result.code))
                }
            }
        }
    }
}