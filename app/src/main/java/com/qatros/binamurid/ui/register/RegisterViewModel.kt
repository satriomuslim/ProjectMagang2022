package com.qatros.binamurid.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.RegisterRequest
import com.qatros.binamurid.utils.SingleLiveEvent
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