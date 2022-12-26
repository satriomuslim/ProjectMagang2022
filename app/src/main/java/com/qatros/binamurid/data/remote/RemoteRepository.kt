package com.qatros.binamurid.data.remote

import com.qatros.binamurid.api.ApiService
import com.qatros.binamurid.base.ResponseResult
import com.qatros.binamurid.data.remote.request.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response

class RemoteRepository(private val apiService: ApiService) {
    suspend fun <T : Any> getResult(
        request: suspend () -> Response<T>
    ): ResponseResult<T> {
        return try {
            val response = request()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ResponseResult.Success(body)
            } else {
                ResponseResult.Error(code = response.code(), errorMsg = response.message())
            }
        } catch (e: HttpException) {
            ResponseResult.Error(code = e.code(), errorMsg = e.message())
        }
    }

    suspend fun postLogin(loginRequest: LoginRequest) = getResult {
        apiService.postLogin(loginRequest)
    }

    suspend fun postRegister(registerRequest: RegisterRequest) = getResult {
        apiService.postRegister(registerRequest)
    }

    suspend fun postForgotPassword(forgotPasswordRequest: ForgotPasswordRequest) = getResult {
        apiService.postResetPassword(forgotPasswordRequest)
    }

    suspend fun getListChild(token: String, type: String) = getResult {
        apiService.getListChild(token, type)
    }

    suspend fun postAddChild(
        token: String,
        fullName: RequestBody,
        school: RequestBody,
        birthOfDate: RequestBody,
        file: MultipartBody.Part
    ) = getResult {
        apiService.postAddChild(token, fullName, school, birthOfDate, file)
    }

    suspend fun getInviteChildren(token: String, childrenId: Int) = getResult {
        apiService.getInviteChildren(token, childrenId)
    }

    suspend fun postInviteChildren(token: String, inviteChildRequest: InviteChildRequest) =
        getResult {
            apiService.postInviteChildren(token, inviteChildRequest)
        }

    suspend fun editProfile(token: String, fullName: RequestBody, email: RequestBody) = getResult {
        apiService.editProfileUser(token, fullName, email)
    }

    suspend fun getListPedagogue(token: String, childrenId: Int) = getResult {
        apiService.getPedagogueByChildId(token, childrenId)
    }

    suspend fun getReport(token: String, date: String, childrenId: Int, userId: Int) = getResult {
        apiService.getReport(token, date, childrenId, userId)
    }

    suspend fun postSubject(token: String, subjectRequest: SubjectRequest) = getResult {
        apiService.postSubject(token, subjectRequest)
    }

    suspend fun postReport(
        token: String,
        childrenId: Int,
        userId: Int,
        addReportRequest: AddReportRequest
    ) = getResult {
        apiService.postReport(token, childrenId, addReportRequest)
    }

    suspend fun postAddRoleUser(token: String, addRoleRequest: AddRoleRequest) = getResult {
        apiService.postAddRoleUser(token, addRoleRequest)
    }

    suspend fun editAvatar(token: String, file: MultipartBody.Part) = getResult {
        apiService.editAvatar(token, file)
    }

    suspend fun confirmProfileChild(token: String, inviteChildRequest: InviteChildRequest) =
        getResult {
            apiService.confirmProfileChild(token, inviteChildRequest)
        }

    suspend fun getHistoryParent(token: String) = getResult {
        apiService.getHistoryParent(token)
    }

    suspend fun getHomeParent(token: String) = getResult {
        apiService.getHomeParent(token)
    }

    suspend fun getHistoryPedagogue(token: String) = getResult {
        apiService.getHistoryPedagogue(token)
    }

    suspend fun getHomePedagogue(token: String) = getResult {
        apiService.getHomePedagogue(token)
    }

    suspend fun getAllReportParent(token: String) = getResult {
        apiService.getAllReportParent(token)
    }

    suspend fun getAllReportPedagogue(token: String) = getResult {
        apiService.getAllReportPedagogue(token)
    }

    suspend fun postMessageChat(token: String, roomId: Int, addChatRequest: AddChatRequest) =
        getResult {
            apiService.postMessageChat(token, roomId, addChatRequest)
        }

    suspend fun getPrivateRoomChat(token: String) = getResult {
        apiService.getPrivateRoomChat(token)
    }

    suspend fun getMessageChat(token: String, userId: Int) = getResult {
        apiService.getMessageChat(token, userId)
    }

    suspend fun editChildrenProfile(
        token: String,
        childrenId: Int,
        fullName: RequestBody,
        school: RequestBody,
        image: MultipartBody.Part?
    ) = getResult {
        apiService.editChildrenProfile(token, childrenId, fullName, school, image)
    }

    suspend fun editPassword(token: String, changePasswordRequest: ChangePasswordRequest) = getResult {
        apiService.editPassword(token, changePasswordRequest)
    }

    suspend fun resendEmail(resendEmailRequest: ResendEmailRequest) = getResult {
        apiService.resendEmail(resendEmailRequest)
    }

    suspend fun confirmToken(confirmTokenRequest: ConfirmTokenRequest) = getResult {
        apiService.confirmEmail(confirmTokenRequest)
    }
}