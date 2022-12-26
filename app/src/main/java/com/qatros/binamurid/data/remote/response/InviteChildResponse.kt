package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class InviteChildResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("children") val children: ChildrenInvitation
)

data class ChildrenInvitation (
    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("children_id") val children_id : Int,
    @SerializedName("invitation_token") val invitation_token : String,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)