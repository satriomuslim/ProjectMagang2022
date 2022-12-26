package com.qatros.binamurid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.LoginRequest
import com.qatros.binamurid.data.remote.request.ResendEmailRequest
import com.qatros.binamurid.data.remote.response.LoginRegisterResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val loginSuccess = MutableLiveData<SingleLiveEvent<LoginRegisterResponse>>()
    fun observeLoginSuccess(): LiveData<SingleLiveEvent<LoginRegisterResponse>> = loginSuccess

    private val loginError = MutableLiveData<SingleLiveEvent<Pair<String, Int>>>()
    fun observeLoginError(): LiveData<SingleLiveEvent<Pair<String, Int>>> = loginError

    private val resendEmailSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeResendEmailSuccess(): LiveData<SingleLiveEvent<Boolean>> = resendEmailSuccess

    fun postLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            when(val result = repository.postLogin(loginRequest)) {
                is ResponseResult.Success -> {
                    loginSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    val errormsg = when(result.code) {
                        400 -> "Bad Request"
                        403 -> "Invalid Email Confirmation"
                        422 -> "Invalid Input Data"
                        else -> "Terjadi Error"
                    }
                    loginError.postValue(SingleLiveEvent(Pair(errormsg, result.code)))
                }
            }
        }
    }

    fun resendEmail(resendEmailRequest: ResendEmailRequest) {
        viewModelScope.launch {
            when(val result = repository.resendEmail(resendEmailRequest)) {
                is ResponseResult.Success -> {
                    resendEmailSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}