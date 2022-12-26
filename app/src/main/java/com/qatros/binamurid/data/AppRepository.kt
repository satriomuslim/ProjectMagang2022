package com.qatros.binamurid.data

import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.local.LocalRepository
import com.qatros.binamurid.data.remote.RemoteRepository
import com.qatros.binamurid.data.remote.request.*
import com.qatros.binamurid.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody

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

    suspend fun getListChild(token: String, type: String) : ResponseResult<ListChildResponse> {
        return remoteRepository.getListChild(token, type)
    }

    suspend fun postAddChild(token: String, fullName: RequestBody, school: RequestBody, birthOfDate: RequestBody, file: MultipartBody.Part) : ResponseResult<Any> {
        return remoteRepository.postAddChild(token, fullName, school, birthOfDate, file)
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

    suspend fun getHistoryParent(token: String): ResponseResult<HistoryResponse> {
        return remoteRepository.getHistoryParent(token)
    }

    suspend fun getHomeParent(token: String): ResponseResult<HistoryResponse> {
        return remoteRepository.getHomeParent(token)
    }

    suspend fun getHistoryPedagogue(token: String): ResponseResult<HistoryResponse> {
        return remoteRepository.getHistoryPedagogue(token)
    }

    suspend fun getHomePedagogue(token: String): ResponseResult<HistoryResponse> {
        return remoteRepository.getHomePedagogue(token)
    }

    suspend fun getAllReportParent(token: String): ResponseResult<ReportResponse> {
        return remoteRepository.getAllReportParent(token)
    }

    suspend fun getAllReportPedagogue(token: String): ResponseResult<ChildrenReportResponse> {
        return remoteRepository.getAllReportPedagogue(token)
    }

    suspend fun postMessageChat(token: String, roomId: Int, addChatRequest: AddChatRequest): ResponseResult<AddChatResponse> {
        return remoteRepository.postMessageChat(token, roomId, addChatRequest)
    }

    suspend fun getPrivateRoomChat(token: String): ResponseResult<AllRoomChatResponse> {
        return remoteRepository.getPrivateRoomChat(token)
    }

    suspend fun getMessageChat(token: String, userId: Int): ResponseResult<AllChatResponse> {
        return remoteRepository.getMessageChat(token, userId)
    }

    suspend fun editChildrenProfile(token: String,
                                    childrenId: Int,
                                    fullName: RequestBody,
                                    school: RequestBody,
                                    image: MultipartBody.Part?) : ResponseResult<Any> {
        return remoteRepository.editChildrenProfile(token, childrenId, fullName, school, image)
    }

    suspend fun editPassword(token: String, changePasswordRequest: ChangePasswordRequest) : ResponseResult<Any> {
        return remoteRepository.editPassword(token, changePasswordRequest)
    }

    suspend fun resendEmail(resendEmailRequest: ResendEmailRequest): ResponseResult<Any> {
        return remoteRepository.resendEmail(resendEmailRequest)
    }

    suspend fun confirmToken(confirmTokenRequest: ConfirmTokenRequest) : ResponseResult<Any> {
        return remoteRepository.confirmToken(confirmTokenRequest)
    }
}