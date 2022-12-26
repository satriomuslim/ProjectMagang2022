package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

class AddRoleResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: RoleData
)

data class RoleData (
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("email") val email : String,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("role") val role : List<String>,
    @SerializedName("confirmed") val confirmed : Boolean,
    @SerializedName("confirm_token") val confirm_token : String,
    @SerializedName("avatar") val avatar : String
)

