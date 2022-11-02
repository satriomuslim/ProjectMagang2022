package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

class LoginRegisterResponse (
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
    @SerializedName("no_hp") val no_hp : String,
    @SerializedName("address") val address : String,
    @SerializedName("dateofbirth") val dateofbirth : String,
    @SerializedName("role") val role : List<String>,
    @SerializedName("avatar") val avatar : String
)