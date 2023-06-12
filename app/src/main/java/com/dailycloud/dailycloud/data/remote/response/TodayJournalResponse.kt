package com.dailycloud.dailycloud.data.remote.response

import com.dailycloud.dailycloud.data.local.model.Journal
import com.google.gson.annotations.SerializedName

data class TodayJournalResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("hasUploadedJournal")
	val hasUploadedJournal: HasUploadedJournal? = null
)

data class HasUploadedJournal(

	@field:SerializedName("journal")
	val journal: Journal? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
)