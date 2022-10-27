package com.qatros.qtn_bina_murid.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListChildResponse(
    @SerializedName("children") val children: List<Children>
)

@Parcelize
data class Children (
    @SerializedName("id") val id : Int,
    @SerializedName("fullname") val fullname : String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("photo_profile") val photo_profile : String,
    @SerializedName("school") val school : String,
    @SerializedName("no_hp") val no_hp : Int,
    @SerializedName("dateofbirth") val dateofbirth : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
) : Parcelable