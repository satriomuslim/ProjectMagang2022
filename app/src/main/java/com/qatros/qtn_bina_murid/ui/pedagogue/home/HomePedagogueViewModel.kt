package com.qatros.qtn_bina_murid.ui.pedagogue.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.response.HistoryResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
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