package com.qatros.binamurid.data.remote.request

import com.google.gson.annotations.SerializedName

data class ResendEmailRequest(
    @SerializedName("email") val email: String
)