package com.qatros.qtn_bina_murid.data.remote.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("role") val role : String
)