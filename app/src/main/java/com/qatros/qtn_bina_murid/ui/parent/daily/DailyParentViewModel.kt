package com.qatros.qtn_bina_murid.ui.parent.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.response.ListChildResponse
import com.qatros.qtn_bina_murid.data.remote.response.ListPedagogueResponse
import com.qatros.qtn_bina_murid.data.remote.response.ReportResponse
import kotlinx.coroutines.launch

class DailyParentViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val getChildListSuccess = MutableLiveData<ListChildResponse?>()
    fun observeGetChildListSuccess() : LiveData<ListChildResponse?> = getChildListSuccess

    private val getPedagogueSuccess = MutableLiveData<ListPedagogueResponse?>()
    fun observeGetPedagogueSuccess() : LiveData<ListPedagogueResponse?> = getPedagogueSuccess

    private val getReportParent = MutableLiveData<ReportResponse?>()
    fun observeGetReportParent() : LiveData<ReportResponse?> = getReportParent

    fun getChildList(token: String) {
        viewModelScope.launch {
            when(val result = repository.getListChild(token)) {
                is ResponseResult.Success -> {
                    getChildListSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
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
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun getReportParent(token: String, date: String, childrenId: Int, userId: Int) {
        viewModelScope.launch {
            when(val result = repository.getReportParent(token, date, childrenId, userId)) {
                is ResponseResult.Success -> {
                    getReportParent.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

}