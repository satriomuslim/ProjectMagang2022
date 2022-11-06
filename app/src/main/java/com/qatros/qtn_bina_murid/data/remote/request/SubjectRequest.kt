package com.qatros.qtn_bina_murid.data.remote.request

import com.google.gson.annotations.SerializedName

data class SubjectRequest(
    @SerializedName("subject") val subject: Subject
)

data class Subject (
    @SerializedName("name") val name : String
)