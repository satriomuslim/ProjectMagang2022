package com.qatros.qtn_bina_murid.ui.resetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.ForgotPasswordRequest
import com.qatros.qtn_bina_murid.data.remote.response.ForgotPasswordResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ResetPasswordViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val forgotPasswordSuccess = MutableLiveData<ForgotPasswordResponse?>()
    fun observeForgotPasswordSuccess(): LiveData<ForgotPasswordResponse?> = forgotPasswordSuccess

    fun postForgotPassword(forgotPasswordRequest: ForgotPasswordRequest) {
        viewModelScope.launch {
            when (val result = repository.postForgotPassword(forgotPasswordRequest)) {
                is ResponseResult.Success -> {
                    forgotPasswordSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}