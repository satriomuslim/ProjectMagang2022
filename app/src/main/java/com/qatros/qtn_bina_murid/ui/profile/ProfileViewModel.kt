package com.qatros.qtn_bina_murid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.response.ProfileResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ProfileViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val imageFile = MutableLiveData<File>()
    fun observeImageFile(): LiveData<File> = imageFile

    private val indicator = MutableLiveData<Boolean>()
    fun observeIndicator(): LiveData<Boolean> = indicator

    private val sendData = MutableLiveData<Pair<String, String>>()
    fun observeSendData(): LiveData<Pair<String, String>> = sendData

    private val editProfileSuccess = MutableLiveData<SingleLiveEvent<ProfileResponse?>>()
    fun observeEditProfileSuccess(): LiveData<SingleLiveEvent<ProfileResponse?>> = editProfileSuccess

    fun sendData(name: String, avatar: String) {
        viewModelScope.launch {
            sendData.postValue(Pair(name, avatar))
        }
    }
    fun sendImageFile(image: File) {
        viewModelScope.launch {
            imageFile.postValue(image)
        }
    }

    fun indicatorProfile(visible: Boolean) {
        viewModelScope.launch {
            indicator.postValue(visible)
        }
    }

    fun editProfile(
        token: String,
        userId: Int,
        fullName: RequestBody,
        telp: RequestBody,
        address: RequestBody,
        dateOfBirth: RequestBody,
        file: MultipartBody.Part?
    ) {
        viewModelScope.launch {
            when(val result = repository.editProfile(token, userId, fullName, telp, address, dateOfBirth, file)) {
                is ResponseResult.Success -> {
                    editProfileSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}