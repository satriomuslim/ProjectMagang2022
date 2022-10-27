package com.qatros.qtn_bina_murid.data.remote

import com.qatros.qtn_bina_murid.api.ApiService
import com.qatros.qtn_bina_murid.base.ResponseResult
import com.qatros.qtn_bina_murid.data.remote.request.ForgotPasswordRequest
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response

class RemoteRepository(private val apiService: ApiService) {
    suspend fun <T : Any> getResult (
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

    suspend fun getListChild(token: String) = getResult {
        apiService.getListChild(token)
    }

    suspend fun postAddChild(token: String, fullName: RequestBody, nickName: RequestBody, school: RequestBody, birthOfDate: RequestBody, file: MultipartBody.Part) = getResult {
        apiService.postAddChild(token, fullName, nickName, school, birthOfDate, file)
    }
}