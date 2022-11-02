package com.qatros.qtn_bina_murid.ui.pedagogue.scanChildren

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.InviteChildRequest
import com.qatros.qtn_bina_murid.data.remote.response.InviteChildResponse
import kotlinx.coroutines.launch

class ScanChildrenViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val inviteChildSuccess = MutableLiveData<InviteChildResponse?>()
    fun observeInviteChildSuccess() : LiveData<InviteChildResponse?> = inviteChildSuccess

    fun postInviteChild(token: String, inviteChildRequest: InviteChildRequest) {
        viewModelScope.launch {
            when(val result = repository.postInviteChildren(token, inviteChildRequest)) {
                is ResponseResult.Success -> {
                    Log.e("TAG", "postInviteChild: ${result.data}", )
                    inviteChildSuccess.postValue(result.data)
                }
                is ResponseResult.Error -> {
                    Log.e("TAG", "postInviteChildERROR: ${result.errorMsg} ${result.code}", )
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}