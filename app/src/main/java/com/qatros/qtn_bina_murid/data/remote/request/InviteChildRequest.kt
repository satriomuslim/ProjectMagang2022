package com.qatros.qtn_bina_murid.data.remote.request

import com.google.gson.annotations.SerializedName

data class InviteChildRequest(
    @SerializedName("invitation_token") val invitation_token: String
)