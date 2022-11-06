package com.qatros.qtn_bina_murid.data.remote.request

import com.google.gson.annotations.SerializedName

data class AddReportRequest(
    @SerializedName("description") val description: String,
    @SerializedName("score") val score: Int
)