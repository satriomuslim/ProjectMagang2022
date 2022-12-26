package com.qatros.binamurid.data.remote.request

import com.google.gson.annotations.SerializedName

class DailyReportRequest (
    @SerializedName("id") val id : Int,
    @SerializedName("date") val date : Int,
    @SerializedName("description") val description : String,
    @SerializedName("score") val score : Int,
    @SerializedName("created_at") val created_at : Int,
    @SerializedName("updated_at") val updated_at : Int,
    @SerializedName("user_id") val user_id : Int,
    @SerializedName("children_id") val children_id : Int,
    @SerializedName("subject_id") val subject_id : Int
)