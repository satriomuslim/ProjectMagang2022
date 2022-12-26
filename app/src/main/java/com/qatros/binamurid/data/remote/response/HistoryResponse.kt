package com.qatros.binamurid.data.remote.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class HistoryResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItem?> = listOf(),

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable

@Parcelize
data class DataItem(

	@field:SerializedName("recipient_type")
	val recipientType: String? = null,

	@field:SerializedName("trackable_id")
	val trackableId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("owner_id")
	val ownerId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("trackable_type")
	val trackableType: String? = null,

	@field:SerializedName("parameters")
	val parameters: Parameters? = null,

	@field:SerializedName("owner_type")
	val ownerType: String? = null,

	@field:SerializedName("key")
	val key: String? = null,

	@field:SerializedName("recipient_id")
	val recipientId: Int? = null
) : Parcelable

@Parcelize
data class Activity(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("children_name")
	val childrenName: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("pedagogue_name")
	val pedagogueName: String? = null,

	@field:SerializedName("report_id")
	val reportId: Int? = null,

	@field:SerializedName("subject")
	val subject: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("children_id")
	val childrenId: Int? = null,

	@field:SerializedName("avatar_children")
	val avatarChildren: String? = null
) : Parcelable

@Parcelize
data class Parameters(

	@field:SerializedName("activity")
	val activity: Activity? = null
) : Parcelable
