package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

data class InvitationTokenResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("invitation_token") val invitation_token: String?
)