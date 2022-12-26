package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddChatResponse(
    @SerializedName("data") val data : ChatData
)

data class ChatData (
    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("room_id") val room_id : Int,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)