package com.qatros.binamurid.data.remote.request

import com.google.gson.annotations.SerializedName

data class AddReportRequest(
    @SerializedName("description") val description: String,
    @SerializedName("score") val score: Int,
    @SerializedName("date") val date: String
)