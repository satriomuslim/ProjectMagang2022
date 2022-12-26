package com.qatros.binamurid.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AllRoomChatResponse(
    @SerializedName("data") val data : RoomData
)

@Parcelize
data class RoomData (
    @SerializedName("user_available") val user_available : List<User_available>? = null,
    @SerializedName("private_room") val private_room : List<Private_room>? = null
) : Parcelable

@Parcelize
data class Last_message (

    @SerializedName("message_id") val message_id : Int? = null,
    @SerializedName("content") val content : String? = null,
    @SerializedName("created_at") val created_at : String? = null
): Parcelable

@Parcelize
data class Private_room (

    @SerializedName("room_id") val room_id : Int? = null,
    @SerializedName("recipient_id") val recipient_id : Int? = null,
    @SerializedName("name") val name : String? = null,
    @SerializedName("last_message") val last_message : Last_message? = null,
    @SerializedName("unread_message") val unread_message : Int? = null
) : Parcelable

@Parcelize
data class User_available (
    @SerializedName("id") val id : Int? = null,
    @SerializedName("fullname") val fullname : String? = null,
    @SerializedName("avatar") val avatar : String? = null
): Parcelable
