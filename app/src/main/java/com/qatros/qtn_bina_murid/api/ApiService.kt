package com.qatros.qtn_bina_murid.api

import com.qatros.qtn_bina_murid.data.remote.request.DailyReportRequest
import com.qatros.qtn_bina_murid.data.remote.request.ForgotPasswordRequest
import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.data.remote.response.DailyReportResponse
import com.qatros.qtn_bina_murid.data.remote.response.ForgotPasswordResponse
import com.qatros.qtn_bina_murid.data.remote.response.InvitationTokenResponse
import com.qatros.qtn_bina_murid.data.remote.response.ListChildResponse
import com.qatros.qtn_bina_murid.data.remote.response.LoginRegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/v1/auth/sessions/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<LoginRegisterResponse>

    @POST("api/v1/auth/registers")
    suspend fun postRegister(@Body registerRequest: RegisterRequest): Response<LoginRegisterResponse>

    @POST("api/v1/password/forgot")
    suspend fun postResetPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @GET("api/v1/children/list")
    suspend fun getListChild(@Header("Authorization") token: String): Response<ListChildResponse>

    @Multipart
    @POST("api/v1/children/add")
    suspend fun postAddChild(
        @Header("Authorization") token: String,
        @Part("fullname") fullname: RequestBody,
        @Part("nickname") nickname: RequestBody,
        @Part("school") school: RequestBody,
        @Part("dateofbirth") dateOfBirth: RequestBody,
        @Part file: MultipartBody.Part
    ) : Response<Any>

    @GET("api/v1/report")
    suspend fun getAllDataDailyReport(@Body dailyReportRequest: DailyReportRequest): Response<DailyReportResponse>

    @POST("api/v1/children/invite")
    suspend fun postInviteChildren()

    @GET("api/v1/children/(children_id)/invitation")
    suspend fun getInviteChildren(@Path("children_id") childrenId: Int) : Response<InvitationTokenResponse>
}