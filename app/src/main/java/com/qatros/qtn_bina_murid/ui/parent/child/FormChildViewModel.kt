package com.qatros.qtn_bina_murid.ui.parent.child

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FormChildViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val addChildSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeAddChildSuccess() : LiveData<SingleLiveEvent<Boolean>> = addChildSuccess

    private val editChildSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeEditChildSuccess() : LiveData<SingleLiveEvent<Boolean>> = editChildSuccess

    fun postAddChild(token: String, fullName: RequestBody, nickName: RequestBody, school: RequestBody, birthOfDate: RequestBody, file: MultipartBody.Part) {
        viewModelScope.launch {
            when(val result = repository.postAddChild(token, fullName, nickName, school, birthOfDate, file)) {
                is ResponseResult.Success -> {
                    addChildSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun editChildrenProfile(token: String,
                            childrenId: Int,
                            fullName: RequestBody,
                            nickName: RequestBody,
                            school: RequestBody,
                            image: MultipartBody.Part?) {
        viewModelScope.launch {
            when(val result = repository.editChildrenProfile(token, childrenId, fullName, nickName, school, image)) {
                is ResponseResult.Success -> {
                    editChildSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}