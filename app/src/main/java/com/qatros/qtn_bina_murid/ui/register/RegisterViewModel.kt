package com.qatros.qtn_bina_murid.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.data.remote.response.LoginRegisterResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val RegisterSuccess = MutableLiveData<SingleLiveEvent<LoginRegisterResponse?>>()
    fun observeRegisterSuccess(): LiveData<SingleLiveEvent<LoginRegisterResponse?>> = RegisterSuccess

    fun postRegister(RegisterRequest: RegisterRequest) {
        viewModelScope.launch {
            when(val result = repository.postRegister(RegisterRequest)) {
                is ResponseResult.Success -> {
                    RegisterSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}