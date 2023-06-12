package com.dailycloud.dailycloud.data.remote.response

import com.dailycloud.dailycloud.data.local.model.Journal
import com.google.gson.annotations.SerializedName

data class JournalResponse(

	@field:SerializedName("journal")
	val journal: Journal? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)