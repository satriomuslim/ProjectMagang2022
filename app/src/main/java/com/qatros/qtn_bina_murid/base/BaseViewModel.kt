package com.qatros.qtn_bina_murid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qatros.qtn_bina_murid.utils.SingleLiveEvent

abstract class BaseViewModel: ViewModel() {
    protected val isError = MutableLiveData<SingleLiveEvent<String>>()
    fun observeError() : LiveData<SingleLiveEvent<String>> = isError
}