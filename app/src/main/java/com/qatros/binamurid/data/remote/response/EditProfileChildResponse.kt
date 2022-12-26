package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class EditProfileChildResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: Item
)

data class Item (
    @SerializedName("user_id") val childrenId : Int?,
    @SerializedName("fullname") val fullname : String?,
    @SerializedName("school") val address : String?,
    @SerializedName("date") val date : Date?,
    @SerializedName("avatar") val avatar : String?
)