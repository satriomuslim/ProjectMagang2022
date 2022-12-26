package com.qatros.binamurid.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.binamurid.base.BaseViewModel
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.AppRepository
import com.qatros.binamurid.data.remote.request.AddChatRequest
import com.qatros.binamurid.data.remote.response.AddChatResponse
import com.qatros.binamurid.data.remote.response.AllChatResponse
import com.qatros.binamurid.data.remote.response.AllRoomChatResponse
import com.qatros.binamurid.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val postMessageSuccess = MutableLiveData<SingleLiveEvent<AddChatResponse>>()
    fun observePostMessageSuccess(): LiveData<SingleLiveEvent<AddChatResponse>> = postMessageSuccess

    private val getPrivateRoomSuccess = MutableLiveData<SingleLiveEvent<AllRoomChatResponse>>()
    fun observeGetPrivateRoomSuccess(): LiveData<SingleLiveEvent<AllRoomChatResponse>> =
        getPrivateRoomSuccess

    private val getAllMessageSuccess = MutableLiveData<SingleLiveEvent<AllChatResponse>>()
    fun observeGetAllMessageSuccess(): LiveData<SingleLiveEvent<AllChatResponse>> =
        getAllMessageSuccess

    fun postMessage(token: String, roomId: Int, addChatRequest: AddChatRequest) {
        viewModelScope.launch {
            when (val result = repository.postMessageChat(token, roomId, addChatRequest)) {
                is ResponseResult.Success -> {
                    postMessageSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getPrivateRoom(token: String) {
        viewModelScope.launch {
            when (val result = repository.getPrivateRoomChat(token)) {
                is ResponseResult.Success -> {
                    Log.e("TAG", "getPrivateRoom: ${result.data.data}", )
                    getPrivateRoomSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }

    fun getAllMessage(token: String, userId: Int) {
        viewModelScope.launch {
            when (val result = repository.getMessageChat(token, userId)) {
                is ResponseResult.Success -> {
                    getAllMessageSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(SingleLiveEvent(result.errorMsg ?: "Terjadi Kesalahan"))
                }
            }
        }
    }
}