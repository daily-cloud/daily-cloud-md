package com.dailycloud.dailycloud.data.remote.response

import com.dailycloud.dailycloud.data.local.model.User
import com.google.gson.annotations.SerializedName

data class UpdateUserResponse(

	@field:SerializedName("data")
	val data: User? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)