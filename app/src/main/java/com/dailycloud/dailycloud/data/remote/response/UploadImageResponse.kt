package com.dailycloud.dailycloud.data.remote.response

import com.google.gson.annotations.SerializedName

data class UploadImageResponse(

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
