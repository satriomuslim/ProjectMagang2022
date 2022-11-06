package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListPedagogueResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Pedagogue>
)

data class Pedagogue (
    @SerializedName("user_id") val user_id : Int = 0,
    @SerializedName("fullname") val fullname : String? = null,
    @SerializedName("avatar") val avatar : String? = null
)