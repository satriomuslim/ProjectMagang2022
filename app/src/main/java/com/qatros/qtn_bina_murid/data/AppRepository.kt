package com.qatros.qtn_bina_murid.data

import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.local.LocalRepository
import com.qatros.qtn_bina_murid.data.remote.RemoteRepository
import com.qatros.qtn_bina_murid.data.remote.request.ForgotPasswordRequest
import com.qatros.qtn_bina_murid.data.remote.request.InviteChildRequest
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response

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

    suspend fun getInviteChildren(token: String, childrenId: Int): ResponseResult<InvitationTokenResponse> {
        return remoteRepository.getInviteChildren(token, childrenId)
    }

    suspend fun postInviteChildren(token: String, inviteChildRequest: InviteChildRequest) : ResponseResult<InviteChildResponse> {
        return remoteRepository.postInviteChildren(token, inviteChildRequest)
    }

    suspend fun editProfile(token: String, userId: Int, fullName: RequestBody, telp: RequestBody, address: RequestBody, dateOfBirth: RequestBody, file: MultipartBody.Part?) : ResponseResult<ProfileResponse> {
        return remoteRepository.editProfile(token, userId, fullName, telp, address, dateOfBirth, file)
    }
}