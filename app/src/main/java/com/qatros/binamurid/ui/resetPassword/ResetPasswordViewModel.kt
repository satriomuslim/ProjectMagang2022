package com.qatros.binamurid.ui.resetPassword

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.ForgotPasswordRequest
import com.qatros.binamurid.data.remote.response.ForgotPasswordResponse
import com.qatros.binamurid.utils.SingleLiveEvent
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