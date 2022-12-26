package com.qatros.binamurid.ui.pedagogue.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.response.HistoryResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class HomePedagogueViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val getHomeSuccess = MutableLiveData<SingleLiveEvent<HistoryResponse>>()
    fun observeHomeSuccess(): MutableLiveData<SingleLiveEvent<HistoryResponse>> = getHomeSuccess

    private val isErrorGetReport = MutableLiveData<SingleLiveEvent<Int>>()
    fun observeErrorGetReport() : LiveData<SingleLiveEvent<Int>> = isErrorGetReport

    fun getHomePedagogue(token: String) {
        viewModelScope.launch {
            when(val result = repository.getHomePedagogue(token)) {
                is ResponseResult.Success -> {
                    getHomeSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isErrorGetReport.postValue(SingleLiveEvent(result.code))
                }
            }
        }
    }
}