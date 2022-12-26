package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class ReportResponse (
    @SerializedName("code") val code : Int,
    @SerializedName("message") val message : String,
    @SerializedName("data") val data : List<Report>
        )

data class Report (
    @SerializedName("report_id") val report_id : Int = 0,
    @SerializedName("user_id") val user_id : Int? = null,
    @SerializedName("children_id") val children_id : Int? = null,
    @SerializedName("fullname") val fullname : String? = null,
    @SerializedName("subject") val subject : String? = null,
    @SerializedName("description") val description : String? = null,
    @SerializedName("score") val score : Int? = null,
    @SerializedName("created_at") val created_at : String? = null
)