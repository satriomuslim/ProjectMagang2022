package com.qatros.qtn_bina_murid.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListChildResponse(
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<Children>
)

@Parcelize
data class Children (
    @SerializedName("children_id") val childrenId : Int,
    @SerializedName("fullname") val fullName : String,
    @SerializedName("nickname") val nickName : String,
    @SerializedName("school") val school : String,
    @SerializedName("date of birth") val dateOfBirth : String,
    @SerializedName("avatar") val avatar : String
) : Parcelable