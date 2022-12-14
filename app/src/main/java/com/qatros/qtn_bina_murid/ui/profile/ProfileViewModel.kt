package com.qatros.qtn_bina_murid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.AddRoleRequest
import com.qatros.qtn_bina_murid.data.remote.request.ChangePasswordRequest
import com.qatros.qtn_bina_murid.data.remote.response.AddRoleResponse
import com.qatros.qtn_bina_murid.data.remote.response.ProfileResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import io.reactivex.Single
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val sendData = MutableLiveData<Pair<String, String>>()
    fun observeSendData(): LiveData<Pair<String, String>> = sendData

    private val editProfileSuccess = MutableLiveData<SingleLiveEvent<ProfileResponse?>>()
    fun observeEditProfileSuccess(): LiveData<SingleLiveEvent<ProfileResponse?>> = editProfileSuccess

    private val editAvatarSuccess = MutableLiveData<SingleLiveEvent<ProfileResponse?>>()
    fun observeEditAvatarSuccess(): LiveData<SingleLiveEvent<ProfileResponse?>> = editAvatarSuccess

    private val addRoleSuccess = MutableLiveData<SingleLiveEvent<AddRoleResponse?>>()
    fun observeAddRoleSuccess(): LiveData<SingleLiveEvent<AddRoleResponse?>> = addRoleSuccess

    private val changePasswordSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeChangePasswordSuccess(): LiveData<SingleLiveEvent<Boolean>> = changePasswordSuccess

    fun sendData(name: String, avatar: String) {
        viewModelScope.launch {
            sendData.postValue(Pair(name, avatar))
        }
    }

    fun editProfile(
        token: String,
        fullName: RequestBody,
        email: RequestBody
    ) {
        viewModelScope.launch {
            when(val result = repository.editProfile(token, fullName, email)) {
                is ResponseResult.Success -> {
                    editProfileSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun editAvatar(token: String, file: MultipartBody.Part) {
        viewModelScope.launch {
            when(val result = repository.editAvatar(token, file)) {
                is ResponseResult.Success -> {
                    editAvatarSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun addRoleUser(token: String, addRoleRequest: AddRoleRequest) {
        viewModelScope.launch {
            when(val result = repository.postAddRoleUser(token, addRoleRequest)) {
                is ResponseResult.Success -> {
                    addRoleSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun editPassword(token: String, changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch {
            when(val result = repository.editPassword(token ,changePasswordRequest)) {
                is ResponseResult.Success -> {
                    changePasswordSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    changePasswordSuccess.postValue(SingleLiveEvent(false))
                }
            }
        }
    }
}