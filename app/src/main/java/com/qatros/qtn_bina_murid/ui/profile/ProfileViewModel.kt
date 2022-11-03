package com.qatros.qtn_bina_murid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.qatros.qtn_bina_murid.base.BaseViewModel
import com.qatros.qtn_bina_murid.data.AppRepository
import kotlinx.coroutines.launch
import java.io.File

class ProfileViewModel(private val repository: AppRepository) : BaseViewModel() {
    private val imageFile = MutableLiveData<File>()
    fun observeImageFile(): LiveData<File> = imageFile

    fun sendImageFile(image: File) {
        viewModelScope.launch {
            imageFile.postValue(image)
        }
    }
}