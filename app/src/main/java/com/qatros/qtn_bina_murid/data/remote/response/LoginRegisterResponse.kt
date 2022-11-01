package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

class LoginRegisterResponse (
    @SerializedName("user") val user : User,
    @SerializedName("token") val token : String,
    @SerializedName("message") val message : String
        )

data class User (

    @SerializedName("id") val id : Int,
    @SerializedName("email") val email : String,
    @SerializedName("password_digest") val password_digest : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("reset_password_token") val reset_password_token : String,
    @SerializedName("reset_password_sent_at") val reset_password_sent_at : String,
    @SerializedName("email_confirmed") val email_confirmed : Boolean,
    @SerializedName("confirm_token") val confirm_token : String,
    @SerializedName("role") val role : String
)