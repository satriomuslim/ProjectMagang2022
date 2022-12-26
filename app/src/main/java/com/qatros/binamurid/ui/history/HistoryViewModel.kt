package com.qatros.binamurid.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.response.HistoryResponse
import com.qatros.binamurid.utils.SingleLiveEvent
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