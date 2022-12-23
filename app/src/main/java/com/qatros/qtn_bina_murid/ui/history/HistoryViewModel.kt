package com.qatros.qtn_bina_murid.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.response.HistoryResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val getHistorySuccess = MutableLiveData<SingleLiveEvent<HistoryResponse>>()
    fun observeHistorySuccess(): MutableLiveData<SingleLiveEvent<HistoryResponse>> = getHistorySuccess

    fun getHistoryParent(token: String) {
        viewModelScope.launch {
            when(val result = repository.getHistoryParent(token)) {
                is ResponseResult.Success -> {
                    getHistorySuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getHistoryPedagogue(token: String) {
        viewModelScope.launch {
            when(val result = repository.getHistoryPedagogue(token)) {
                is ResponseResult.Success -> {
                    getHistorySuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}