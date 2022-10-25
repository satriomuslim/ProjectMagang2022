package com.qatros.qtn_bina_murid.data

import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.local.LocalRepository
import com.qatros.qtn_bina_murid.data.remote.RemoteRepository
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.data.remote.response.LoginRegisterResponse

class AppRepository(private val localRepository: LocalRepository, private val remoteRepository: RemoteRepository) {
    suspend fun postLogin(loginRequest: LoginRequest) : ResponseResult<LoginRegisterResponse> {
        return remoteRepository.postLogin(loginRequest)
    }

    suspend fun postRegister(registerRequest: RegisterRequest) : ResponseResult<LoginRegisterResponse> {
        return remoteRepository.postRegister(registerRequest)
    }
}