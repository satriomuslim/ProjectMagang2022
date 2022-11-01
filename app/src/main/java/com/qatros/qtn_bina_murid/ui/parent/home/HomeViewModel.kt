package com.qatros.qtn_bina_murid.ui.parent.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.response.ListChildResponse
import kotlinx.coroutines.launch
import kotlin.math.log

class HomeViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val getChildListSuccess = MutableLiveData<ListChildResponse?>()
    fun observeGetChildListSuccess() : LiveData<ListChildResponse?> = getChildListSuccess

    private val getChildTokenSuccess = MutableLiveData<String?>()
    fun observeGetChildTokenSuccess() : LiveData<String?> = getChildTokenSuccess

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

    fun getInviteChildren(token: String, childrenId :Int) {
        viewModelScope.launch {
            when(val result = repository.getInviteChildren(token, childrenId)) {
                is ResponseResult.Success -> {
                    Log.e("TAG", "getInviteChildren: ${result.data}", )
                    getChildTokenSuccess.postValue(result.data.invitation_token)
                }
                is ResponseResult.Error -> {
                    Log.e("TAG", "getInviteChildren: ${result.errorMsg}", )
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

}