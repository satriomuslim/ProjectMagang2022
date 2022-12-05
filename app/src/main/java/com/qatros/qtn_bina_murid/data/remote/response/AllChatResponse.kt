package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllChatResponse(
    @SerializedName("data") val data: AllChatData
)

data class AllChatData (
    @SerializedName("room") val room : Room,
    @SerializedName("user_available") val user_available : User_available,
    @SerializedName("messages") val messages : List<Messages>
)

data class Room (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("is_private") val is_private : Boolean,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)

data class User_available (

    @SerializedName("id") val id : Int,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("email") val email : String,
    @SerializedName("password_digest") val password_digest : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String,
    @SerializedName("reset_password_token") val reset_password_token : String,
    @SerializedName("reset_password_sent_at") val reset_password_sent_at : String,
    @SerializedName("email_confirmed") val email_confirmed : Boolean,
    @SerializedName("confirm_token") val confirm_token : String
)

data class Messages (

    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("room_id") val room_id : Int,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)