package com.qatros.binamurid.data.remote.response

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
    @SerializedName("dateofbirth") val dateOfBirth : String? = null,
    @SerializedName("avatar") val avatar : String? = null
) : Parcelable