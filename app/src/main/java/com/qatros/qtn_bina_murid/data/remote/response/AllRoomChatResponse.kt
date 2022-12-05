package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllRoomChatResponse(
    @SerializedName("data") val data : RoomData
)

data class RoomData (
    @SerializedName("user_available") val user_available : List<User_available>,
    @SerializedName("private_room") val private_room : List<Private_room>
)

data class Last_message (

    @SerializedName("message_id") val message_id : Int,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String
)

data class Private_room (

    @SerializedName("room_id") val room_id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("last_message") val last_message : Last_message,
    @SerializedName("unread_message") val unread_message : Int
)

data class User_available (

    @SerializedName("id") val id : Int,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("avatar") val avatar : String
)
