package com.qatros.qtn_bina_murid.data.remote.request

import com.google.gson.annotations.SerializedName

data class AddChatRequest(
    @SerializedName("content") val content: String
)