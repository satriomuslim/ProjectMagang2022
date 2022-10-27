package com.qatros.qtn_bina_murid.data

import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.local.LocalRepository
import com.qatros.qtn_bina_murid.data.remote.RemoteRepository
import com.qatros.qtn_bina_murid.data.remote.request.ForgotPasswordRequest
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.data.remote.response.ForgotPasswordResponse
import com.qatros.qtn_bina_murid.data.remote.response.ListChildResponse
import com.qatros.qtn_bina_murid.data.remote.response.LoginRegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository(private val localRepository: LocalRepository, private val remoteRepository: RemoteRepository) {
    suspend fun postLogin(loginRequest: LoginRequest) : ResponseResult<LoginRegisterResponse> {
        return remoteRepository.postLogin(loginRequest)
    }

    suspend fun postRegister(registerRequest: RegisterRequest) : ResponseResult<LoginRegisterResponse> {
        return remoteRepository.postRegister(registerRequest)
    }

    suspend fun postForgotPassword(forgotPasswordRequest: ForgotPasswordRequest) : ResponseResult<ForgotPasswordResponse> {
        return remoteRepository.postForgotPassword(forgotPasswordRequest)
    }

    suspend fun getListChild(token: String) : ResponseResult<ListChildResponse> {
        return remoteRepository.getListChild(token)
    }

    suspend fun postAddChild(token: String, fullName: RequestBody, nickName: RequestBody, school: RequestBody, birthOfDate: RequestBody, file: MultipartBody.Part) : ResponseResult<Any> {
        return remoteRepository.postAddChild(token, fullName, nickName, school, birthOfDate, file)
    }
}