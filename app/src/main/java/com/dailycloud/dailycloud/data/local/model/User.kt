package com.dailycloud.dailycloud.data.local.model

import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("birthday")
    val birthday: Timestamp? = null,

    @field:SerializedName("uid")
    val uid: String? = null,

    @field:SerializedName("imageUrl")
    val imageUrl: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null
)