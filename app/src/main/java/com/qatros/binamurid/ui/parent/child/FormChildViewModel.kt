package com.qatros.binamurid.ui.parent.child

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FormChildViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val addChildSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeAddChildSuccess() : LiveData<SingleLiveEvent<Boolean>> = addChildSuccess

    private val editChildSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeEditChildSuccess() : LiveData<SingleLiveEvent<Boolean>> = editChildSuccess

    fun postAddChild(token: String, fullName: RequestBody, school: RequestBody, birthOfDate: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            when(val result = repository.postAddChild(token, fullName, school, birthOfDate, file)) {
                is ResponseResult.Success -> {
                    addChildSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun editChildrenProfile(token: String,
                            childrenId: Int,
                            fullName: RequestBody,
                            school: RequestBody,
                            image: MultipartBody.Part?) {
        viewModelScope.launch {
            when(val result = repository.editChildrenProfile(token, childrenId, fullName, school, image)) {
                is ResponseResult.Success -> {
                    editChildSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}