package com.qatros.binamurid.ui.pedagogue.scanChildren

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.InviteChildRequest
import com.qatros.binamurid.data.remote.response.ConfirmProfileChildResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ScanChildrenViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val inviteChildSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeInviteChildSuccess() : LiveData<SingleLiveEvent<Boolean>> = inviteChildSuccess

    private val confirmChild = MutableLiveData<ConfirmProfileChildResponse?>()
    fun observeConfirmChild() : LiveData<ConfirmProfileChildResponse?> = confirmChild

    fun postInviteChild(token: String, inviteChildRequest: InviteChildRequest) {
        viewModelScope.launch {
            when(val result = repository.postInviteChildren(token, inviteChildRequest)) {
                is ResponseResult.Success -> {
                    Log.e("TAG", "postInviteChild: ${result.data}", )
                    inviteChildSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    inviteChildSuccess.postValue(SingleLiveEvent(false))
                }
            }
        }
    }

    fun confirmProfileChild(token: String, inviteChildRequest: InviteChildRequest) {
        viewModelScope.launch {
            when(val result = repository.confirmProfileChild(token, inviteChildRequest)) {
                is ResponseResult.Success -> {
                    confirmChild.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    Log.e("TAG", "postInviteChildERROR: ${result.errorMsg} ${result.code}", )
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}