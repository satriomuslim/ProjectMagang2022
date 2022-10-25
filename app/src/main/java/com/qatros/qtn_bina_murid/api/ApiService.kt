package com.qatros.qtn_bina_murid.api

import com.qatros.qtn_bina_murid.data.remote.request.LoginRequest
import com.qatros.qtn_bina_murid.data.remote.request.RegisterRequest
import com.qatros.qtn_bina_murid.data.remote.response.LoginRegisterResponse
import retrofit2.Response
import retrofit2.http.POST

interface ApiService {

    @POST("api/v1/auth/sessions/login")
    suspend fun postLogin(loginRequest: LoginRequest) : Response<LoginRegisterResponse>

    @POST("api/v1/auth/registers")
    suspend fun postRegister(registerRequest: RegisterRequest) : Response<LoginRegisterResponse>
}