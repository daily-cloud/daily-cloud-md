package com.dailycloud.dailycloud.data.remote.response

import com.google.gson.annotations.SerializedName

data class AddUserResponse(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
