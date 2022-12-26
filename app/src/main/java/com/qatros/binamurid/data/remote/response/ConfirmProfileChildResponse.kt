package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

class ConfirmProfileChildResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("invitation_token") val invitationToken: String,
    @SerializedName("children") val children: ChildrenProfile
)

data class ChildrenProfile (
    @SerializedName("fullname") val fullname : String,
    @SerializedName("avatar") val avatar : String
)