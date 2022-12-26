package com.qatros.binamurid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.AddRoleRequest
import com.qatros.binamurid.data.remote.request.ChangePasswordRequest
import com.qatros.binamurid.data.remote.request.ResendEmailRequest
import com.qatros.binamurid.data.remote.response.AddRoleResponse
import com.qatros.binamurid.data.remote.response.ProfileResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    private val sendEmailSuccess = MutableLiveData<SingleLiveEvent<Boolean>>()
    fun observeSendEmailSuccess(): LiveData<SingleLiveEvent<Boolean>> = sendEmailSuccess

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
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
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
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
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
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
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

    fun resendEmail(resendEmailRequest: ResendEmailRequest) {
        viewModelScope.launch {
            when(val result = repository.resendEmail(resendEmailRequest)) {
                is ResponseResult.Success -> {
                    sendEmailSuccess.postValue(SingleLiveEvent(true))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}