package com.dailycloud.dailycloud.data.remote.response

import com.google.gson.annotations.SerializedName

data class QuotesResponse(

	@field:SerializedName("quote")
	val quote: Quote? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Quote(

	@field:SerializedName("quote")
	val quote: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("quoteId")
	val quoteId: String? = null
)
