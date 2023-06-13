package com.dailycloud.dailycloud.data.remote.response

import com.dailycloud.dailycloud.data.local.model.Journal
import com.google.gson.annotations.SerializedName

data class JournalsResponse(

	@field:SerializedName("journals")
	val data: List<Journal?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)