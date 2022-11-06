package com.qatros.qtn_bina_murid.ui.pedagogue.daily

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.AppRepository
import com.qatros.qtn_bina_murid.data.remote.request.AddReportRequest
import com.qatros.qtn_bina_murid.data.remote.request.SubjectRequest
import kotlinx.coroutines.launch

class DailyPedagogueViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val postSubjectSuccess = MutableLiveData<Boolean>()
    fun observePostSubjectSuccess() : LiveData<Boolean> = postSubjectSuccess

    private val postReportSuccess = MutableLiveData<Boolean>()
    fun observePostReportSuccess() : LiveData<Boolean> = postReportSuccess

    fun postSubject(token: String, subjectRequest: SubjectRequest) {
        viewModelScope.launch {
            when(val result = repository.postSubject(token, subjectRequest)) {
                is ResponseResult.Success -> {
                    postSubjectSuccess.postValue(true)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }

    fun postReport(token: String, childrenId: Int, userId: Int, addReportRequest: AddReportRequest) {
        viewModelScope.launch {
            when(val result = repository.postReport(token, childrenId, userId, addReportRequest)) {
                is ResponseResult.Success -> {
                    postReportSuccess.postValue(true)
                }
                is ResponseResult.Error -> {
                    isError.postValue(result.errorMsg)
                }
            }
        }
    }
}