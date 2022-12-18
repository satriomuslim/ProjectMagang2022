package com.qatros.qtn_bina_murid.data.remote.request

import com.google.gson.annotations.SerializedName

data class ResendEmailRequest(
    @SerializedName("email") val email: String
)