package com.qatros.qtn_bina_murid.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.AddChatRequest
import com.qatros.qtn_bina_murid.data.remote.response.AddChatResponse
import com.qatros.qtn_bina_murid.data.remote.response.AllChatResponse
import com.qatros.qtn_bina_murid.data.remote.response.AllRoomChatResponse
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent
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
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun getPrivateRoom(token: String) {
        viewModelScope.launch {
            when (val result = repository.getPrivateRoomChat(token)) {
                is ResponseResult.Success -> {
                    getPrivateRoomSuccess.postValue(SingleLiveEvent(result.data))
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
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
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}