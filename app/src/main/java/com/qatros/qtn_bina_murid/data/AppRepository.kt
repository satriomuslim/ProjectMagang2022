package com.qatros.qtn_bina_murid.data

import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.local.LocalRepository
import com.qatros.qtn_bina_murid.data.remote.RemoteRepository
import com.qatros.qtn_bina_murid.data.remote.request.*
import com.qatros.qtn_bina_murid.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response

class AppRepository(private val localRepository: LocalRepository, private val remoteRepository: RemoteRepository) {
    suspend fun postLogin(loginRequest: LoginRequest) : ResponseResult<LoginRegisterResponse> {
        return remoteRepository.postLogin(loginRequest)
    }

    suspend fun postRegister(registerRequest: RegisterRequest) : ResponseResult<Any> {
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

    suspend fun editProfile(token: String, fullName: RequestBody, email: RequestBody) : ResponseResult<ProfileResponse> {
        return remoteRepository.editProfile(token, fullName, email)
    }

    suspend fun getPedagogueByChildId(token: String, childrenId: Int) : ResponseResult<ListPedagogueResponse> {
        return remoteRepository.getListPedagogue(token, childrenId)
    }

    suspend fun getReport(token: String, date: String, childrenId: Int, userId: Int) : ResponseResult<ReportResponse> {
        return remoteRepository.getReport(token, date, childrenId, userId)
    }

    suspend fun postSubject(token: String, subjectRequest: SubjectRequest) : ResponseResult<Any> {
        return remoteRepository.postSubject(token, subjectRequest)
    }

    suspend fun postReport(token: String, childrenId: Int, userId: Int, addReportRequest: AddReportRequest) : ResponseResult<Any> {
        return remoteRepository.postReport(token, childrenId, userId, addReportRequest)
    }

    suspend fun postAddRoleUser(token: String, addRoleRequest: AddRoleRequest) : ResponseResult<AddRoleResponse> {
        return remoteRepository.postAddRoleUser(token, addRoleRequest)
    }

    suspend fun editAvatar(token: String, file: MultipartBody.Part) : ResponseResult<ProfileResponse> {
        return remoteRepository.editAvatar(token, file)
    }

    suspend fun confirmProfileChild(token: String, inviteChildRequest: InviteChildRequest) : ResponseResult<ConfirmProfileChildResponse> {
        return remoteRepository.confirmProfileChild(token, inviteChildRequest)
    }

}