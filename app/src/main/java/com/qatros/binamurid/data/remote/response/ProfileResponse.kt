package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Data
)

data class Data (
    @SerializedName("user_id") val userId : Int?,
    @SerializedName("email") val email : String?,
    @SerializedName("fullname") val fullname : String?,
    @SerializedName("address") val address : String?,
    @SerializedName("avatar") val avatar : String?
)