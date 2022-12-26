package com.qatros.binamurid.data.remote.request

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user") val user: UserLogin
)

data class UserLogin(

    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)