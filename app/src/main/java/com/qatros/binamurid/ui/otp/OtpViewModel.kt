package com.qatros.binamurid.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.ConfirmTokenRequest
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class OtpViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val confirmTokenSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeConfirmTokenSuccess(): LiveData<SingleLiveEvent<Boolean>> = confirmTokenSuccess

    fun confirmToken(confirmTokenRequest: ConfirmTokenRequest) {
        viewModelScope.launch {
            when(val result = repository.confirmToken(confirmTokenRequest)) {
                is ResponseResult.Success -> {
                    confirmTokenSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}