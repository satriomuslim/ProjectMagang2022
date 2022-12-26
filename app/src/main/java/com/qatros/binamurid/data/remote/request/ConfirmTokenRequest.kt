package com.qatros.binamurid.data.remote.request

import com.google.gson.annotations.SerializedName

data class ConfirmTokenRequest(
    @SerializedName("confirm_token") val confirmToken: String
)