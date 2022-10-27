package com.qatros.qtn_bina_murid.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.response.LoginRegisterResponse
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val loginSuccess = MutableLiveData<LoginRegisterResponse?>()
    fun observeLoginSuccess(): LiveData<LoginRegisterResponse?> = loginSuccess

    fun postLogin(loginRequest: LoginRequest) {
        viewModelScope.launch {
            when(val result = repository.postLogin(loginRequest)) {
                is ResponseResult.Success -> {
                    loginSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}