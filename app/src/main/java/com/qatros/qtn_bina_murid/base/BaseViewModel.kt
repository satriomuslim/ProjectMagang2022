package com.qatros.qtn_bina_murid.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    protected val isError = MutableLiveData<String>()
    fun observeError() : LiveData<String> = isError
}