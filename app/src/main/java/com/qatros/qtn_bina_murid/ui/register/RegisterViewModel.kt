package com.qatros.qtn_bina_murid.ui.register

import android.util.Log
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
    private val RegisterSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeRegisterSuccess(): LiveData<SingleLiveEvent<Boolean>> = RegisterSuccess

    private val isErrorRegister = MutableLiveData<SingleLiveEvent<String>>()
    fun observeIsErrorRegister(): LiveData<SingleLiveEvent<String>> = isErrorRegister

    fun postRegister(RegisterRequest: RegisterRequest) {
        viewModelScope.launch {
            when(val result = repository.postRegister(RegisterRequest)) {
                is ResponseResult.Success -> {
                    RegisterSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    val errormsg = when(result.code) {
                        400 -> "Bad Request"
                        422 -> "Invalid Input Data atau Data Sudah Terdaftar"
                        else -> "Terjadi Error"
                    }
                    isErrorRegister.postValue(SingleLiveEvent(errormsg))
                }
            }
        }
    }
}