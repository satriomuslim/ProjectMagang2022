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
    @SerializedName("children_id") val childrenId : Int = 0,
    @SerializedName("fullname") val fullName : String? = null,
    @SerializedName("nickname") val nickName : String? = null,
    @SerializedName("school") val school : String? = null,
    @SerializedName("date of birth") val dateOfBirth : String? = null,
    @SerializedName("avatar") val avatar : String? = null
) : Parcelable