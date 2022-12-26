package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

class ForgotPasswordResponse(
    @SerializedName("status") val status : String,
    @SerializedName("token") val token : String,
    @SerializedName("expired_at") val expired_at : String
)