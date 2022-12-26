package com.qatros.binamurid.data.remote.response

import com.google.gson.annotations.SerializedName

data class ChildrenReportResponse(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<ChildrenData>
)

data class ChildrenData(

    @SerializedName("report_id") val report_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("children_id") val children_id: Int,
    @SerializedName("childrens_fullname") val childrensFullname: String,
    @SerializedName("subject") val subject: String,
    @SerializedName("description") val description: String,
    @SerializedName("score") val score: Int,
    @SerializedName("date") val date: String
)