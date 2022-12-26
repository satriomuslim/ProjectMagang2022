package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

class LoginRegisterResponse (
    @SerializedName("error") val error: String,
    @SerializedName("message") val message : String,
    @SerializedName("token") val token : String,
    @SerializedName("data") val data : User
        )

data class User (
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("email") val email : String,
    @SerializedName("confirmed") val confirmed : Boolean,
    @SerializedName("confirm_token") val confirm_token : String,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("subject") val subject : String?,
    @SerializedName("address") val address : String,
    @SerializedName("dateofbirth") val dateofbirth : String,
    @SerializedName("role") val role : List<String>,
    @SerializedName("avatar") val avatar : String
)