package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class InvitationTokenResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("invitation_token") val invitation_token: String?,
    @SerializedName("children") val children : InvDataResponse
)

data class InvDataResponse (

    @SerializedName("fullname") val fullname : String?,
    @SerializedName("avatar") val avatar : String?
)