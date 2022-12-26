package com.qatros.binamurid.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class AllChatResponse(
    @SerializedName("data") val data: MessageData
)

data class MessageData (

    @SerializedName("room") val room : Room,
    @SerializedName("recipient") val recipient : Recipient,
    @SerializedName("messages") val messages : List<Messages>
)

@Parcelize
data class Messages (
    @SerializedName("user_id") val userId: Int,
    @SerializedName("message_id") val message_id : Int,
    @SerializedName("content") val content : String,
    @SerializedName("created_at") val created_at : String
) : Parcelable

data class Recipient (
    @SerializedName("id") val id : Int,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("avatar") val avatar : String
)

data class Room (
    @SerializedName("room_id") val room_id : Int,
    @SerializedName("recipient_id") val recipient_id : Int? = null,
    @SerializedName("name") val name : String,
    @SerializedName("last_message") val last_message : Last_message
)