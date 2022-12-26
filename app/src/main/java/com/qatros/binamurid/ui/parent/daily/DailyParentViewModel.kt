package com.qatros.binamurid.ui.parent.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.response.ListChildResponse
import com.qatros.binamurid.data.remote.response.ListPedagogueResponse
import com.qatros.binamurid.data.remote.response.ReportResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class DailyParentViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val getChildListSuccess = MutableLiveData<SingleLiveEvent<ListChildResponse?>>()
    fun observeGetChildListSuccess() : LiveData<SingleLiveEvent<ListChildResponse?>> = getChildListSuccess

    private val getPedagogueSuccess = MutableLiveData<ListPedagogueResponse?>()
    fun observeGetPedagogueSuccess() : LiveData<ListPedagogueResponse?> = getPedagogueSuccess

    private val getReportParent = MutableLiveData<ReportResponse?>()
    fun observeGetReportParent() : LiveData<ReportResponse?> = getReportParent

    private val getAllReportParent = MutableLiveData<ReportResponse?>()
    fun observeGetAllReportPedagogue() : LiveData<ReportResponse?> = getAllReportParent

    private val isErrorGetReport = MutableLiveData<SingleLiveEvent<Int>>()
    fun observeErrorGetReport() : LiveData<SingleLiveEvent<Int>> = isErrorGetReport

    fun getChildList(token: String, type: String) {
        viewModelScope.launch {
            when(val result = repository.getListChild(token, type)) {
                is ResponseResult.Success -> {
                    getChildListSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getPedagogue(token: String, childrenId: Int) {
        viewModelScope.launch {
            when(val result = repository.getPedagogueByChildId(token, childrenId)) {
                is ResponseResult.Success -> {
                    getPedagogueSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getReportParent(token: String, date: String, childrenId: Int, userId: Int) {
        viewModelScope.launch {
            when(val result = repository.getReport(token, date, childrenId, userId)) {
                is ResponseResult.Success -> {
                    getReportParent.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isErrorGetReport.postValue(SingleLiveEvent(result.code))
                }
            }
        }
    }

    fun getAllReportParent(token: String) {
        viewModelScope.launch {
            when(val result = repository.getAllReportParent(token)) {
                is ResponseResult.Success -> {
                    getAllReportParent.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isErrorGetReport.postValue(SingleLiveEvent(result.code))
                }
            }
        }
    }

}