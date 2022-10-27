package com.qatros.qtn_bina_murid.ui.parent.child

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FormChildViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val addChildSuccess = MutableLiveData<Boolean>()
    fun observeAddChildSuccess() : LiveData<Boolean> = addChildSuccess

    fun postAddChild(token: String, fullName: RequestBody, nickName: RequestBody, school: RequestBody, birthOfDate: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            when(val result = repository.postAddChild(token, fullName, nickName, school, birthOfDate, file)) {
                is ResponseResult.Success -> {
                    addChildSuccess.postValue(true)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}