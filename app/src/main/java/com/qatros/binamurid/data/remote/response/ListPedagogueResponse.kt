package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class ListPedagogueResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<Pedagogue>
)

data class Pedagogue (
    @SerializedName("pedagogue_id") val pedagogue_id : Int = 0,
    @SerializedName("fullname") val fullname : String? = null,
    @SerializedName("avatar") val avatar : String? = null
)