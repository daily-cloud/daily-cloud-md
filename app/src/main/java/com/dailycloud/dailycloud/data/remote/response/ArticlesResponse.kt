package com.dailycloud.dailycloud.data.remote.response

import com.dailycloud.dailycloud.data.local.model.Content
import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("data")
	val data: List<Content?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)