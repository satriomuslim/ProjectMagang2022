package com.qatros.qtn_bina_murid.ui.otp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.ConfirmTokenRequest
import com.qatros.qtn_bina_murid.data.remote.request.ResendEmailRequest
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
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