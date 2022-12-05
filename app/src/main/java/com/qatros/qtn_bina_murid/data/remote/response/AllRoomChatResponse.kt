package com.qatros.qtn_bina_murid.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllRoomChatResponse (
    @SerializedName("private_chat") val private_chat : List<PrivateChat>
        )

data class PrivateChat (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("is_private") val is_private : Boolean,
    @SerializedName("created_at") val created_at : String,
    @SerializedName("updated_at") val updated_at : String
)