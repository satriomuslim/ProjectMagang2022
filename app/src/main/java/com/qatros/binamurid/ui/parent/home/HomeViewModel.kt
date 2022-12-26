package com.qatros.binamurid.ui.parent.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.response.HistoryResponse
import com.qatros.binamurid.data.remote.response.ListChildResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val getChildListSuccess = MutableLiveData<ListChildResponse?>()
    fun observeGetChildListSuccess() : LiveData<ListChildResponse?> = getChildListSuccess

    private val getChildTokenSuccess = MutableLiveData<String?>()
    fun observeGetChildTokenSuccess() : LiveData<String?> = getChildTokenSuccess

    private val getHomeSuccess = MutableLiveData<SingleLiveEvent<HistoryResponse>>()
    fun observeHomeSuccess(): MutableLiveData<SingleLiveEvent<HistoryResponse>> = getHomeSuccess

    private val isErrorGetReport = MutableLiveData<SingleLiveEvent<Int>>()
    fun observeErrorGetReport() : LiveData<SingleLiveEvent<Int>> = isErrorGetReport

    fun getChildList(token: String, type: String) {
        viewModelScope.launch {
            when(val result = repository.getListChild(token, type)) {
                is ResponseResult.Success -> {
                    getChildListSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getInviteChildren(token: String, childrenId :Int) {
        viewModelScope.launch {
            when(val result = repository.getInviteChildren(token, childrenId)) {
                is ResponseResult.Success -> {
                    Log.e("TAG", "getInviteChildren: ${result.data}", )
                    getChildTokenSuccess.postValue(result.data.invitation_token)
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getHomeParent(token: String) {
        viewModelScope.launch {
            when(val result = repository.getHomeParent(token)) {
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