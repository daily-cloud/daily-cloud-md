package com.dailycloud.dailycloud.data.remote.response

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName

data class JournalsResponse(

	@field:SerializedName("data")
	val data: List<JournalPreview?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class JournalPreview(

	@field:SerializedName("date")
	val date: Timestamp? = null,

	@field:SerializedName("journalId")
	val journalId: String? = null,

	@field:SerializedName("title")
	val title: String? = null
)
