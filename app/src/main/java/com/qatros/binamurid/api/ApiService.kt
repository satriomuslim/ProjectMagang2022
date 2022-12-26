package com.qatros.binamurid.api

import com.qatros.binamurid.data.remote.request.*
import com.qatros.binamurid.data.remote.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/v1/user/sessions/login")
    suspend fun postLogin(@Body loginRequest: LoginRequest): Response<LoginRegisterResponse>

    @POST("api/v1/user/registers")
    suspend fun postRegister(@Body registerRequest: RegisterRequest): Response<Any>

    @POST("api/v1/password/forgot")
    suspend fun postResetPassword(@Body forgotPasswordRequest: ForgotPasswordRequest): Response<ForgotPasswordResponse>

    @GET("api/v1/children/list?")
    suspend fun getListChild(
        @Header("Authorization") token: String,
        @Query("type") type: String
    ): Response<ListChildResponse>

    @Multipart
    @POST("api/v1/children/add")
    suspend fun postAddChild(
        @Header("Authorization") token: String,
        @Part("fullname") fullname: RequestBody,
        @Part("school") school: RequestBody,
        @Part("dateofbirth") dateOfBirth: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<Any>

    @POST("api/v1/children/invite")
    suspend fun postInviteChildren(
        @Header("Authorization") token: String,
        @Body inviteChildRequest: InviteChildRequest
    ): Response<InviteChildResponse>

    @GET("api/v1/children/{children_id}/invitation")
    suspend fun getInviteChildren(
        @Header("Authorization") token: String,
        @Path("children_id") childrenId: Int
    ): Response<InvitationTokenResponse>

    @Multipart
    @PUT("api/v1/user/profile/edit")
    suspend fun editProfileUser(
        @Header("Authorization") token: String,
        @Part("fullname") fullname: RequestBody,
        @Part("email") email: RequestBody
    ): Response<ProfileResponse>

    @GET("api/v1/children/pedagogue?")
    suspend fun getPedagogueByChildId(
        @Header("Authorization") token: String,
        @Query("children_id") childrenId: Int
    ): Response<ListPedagogueResponse>

    @GET("api/v1/report/children/date?")
    suspend fun getReport(
        @Header("Authorization") token: String,
        @Query("selected_date") date: String,
        @Query("children_id") childrenId: Int,
        @Query("user_id") userId: Int
    ): Response<ReportResponse>

    @POST("api/v1/subject/create")
    suspend fun postSubject(
        @Header("Authorization") token: String,
        @Body subjectRequest: SubjectRequest
    ): Response<Any>

    @POST("api/v1/report/create")
    suspend fun postReport(
        @Header("Authorization") token: String,
        @Query("children_id") childrenId: Int,
        @Body addReportRequest: AddReportRequest
    ): Response<Any>

    @POST("api/v1/user/profile/role")
    suspend fun postAddRoleUser(
        @Header("Authorization") token: String,
        @Body addRoleRequest: AddRoleRequest
    ): Response<AddRoleResponse>

    @Multipart
    @PUT("api/v1/user/profile/edit/avatar")
    suspend fun editAvatar(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Response<ProfileResponse>

    @POST("api/v1/children/confirm_invitation")
    suspend fun confirmProfileChild(
        @Header("Authorization") token: String,
        @Body inviteChildRequest: InviteChildRequest
    ): Response<ConfirmProfileChildResponse>

    @GET("api/v1/activities/parent/monthly")
    suspend fun getHistoryParent(
        @Header("Authorization") token: String
    ): Response<HistoryResponse>

    @GET("api/v1/activities/parent/daily")
    suspend fun getHomeParent(
        @Header("Authorization") token: String
    ): Response<HistoryResponse>

    @GET("api/v1/activities/pedagogue/monthly")
    suspend fun getHistoryPedagogue(
        @Header("Authorization") token: String
    ): Response<HistoryResponse>

    @GET("api/v1/activities/pedagogue/daily")
    suspend fun getHomePedagogue(
        @Header("Authorization") token: String
    ): Response<HistoryResponse>

    @GET("api/v1/report/parent/all")
    suspend fun getAllReportParent(@Header("Authorization") token: String): Response<ReportResponse>

    @GET("api/v1/report/children/all")
    suspend fun getAllReportPedagogue(@Header("Authorization") token: String): Response<ChildrenReportResponse>

    @POST("api/v1/chat/private/rooms/{room_id}")
    suspend fun postMessageChat(
        @Header("Authorization") token: String,
        @Path("room_id") roomId: Int,
        @Body addChatRequest: AddChatRequest
    ): Response<AddChatResponse>

    @GET("api/v1/chat/private/rooms/all")
    suspend fun getPrivateRoomChat(@Header("Authorization") token: String): Response<AllRoomChatResponse>

    @GET("api/v1/chat/private/rooms?")
    suspend fun getMessageChat(
        @Header("Authorization") token: String,
        @Query("user_id") userId: Int
    ): Response<AllChatResponse>

    @Multipart
    @PATCH("api/v1/children/edit?")
    suspend fun editChildrenProfile(
        @Header("Authorization") token: String,
        @Query("children_id") childrenId: Int,
        @Part("fullname") fullname: RequestBody,
        @Part("school") school: RequestBody,
        @Part file: MultipartBody.Part?
    ) : Response<Any>

    @POST("api/v1/password/change")
    suspend fun editPassword(
        @Header("Authorization") token: String,
        @Body changePasswordRequest: ChangePasswordRequest
    ) : Response<Any>

    @POST("api/v1/user/email/resend")
    suspend fun resendEmail(
        @Body resendEmailRequest: ResendEmailRequest
    ) : Response<Any>

    @POST("api/v1/user/email/confirm")
    suspend fun confirmEmail(
        @Body confirmTokenRequest: ConfirmTokenRequest
    ) : Response<Any>
}