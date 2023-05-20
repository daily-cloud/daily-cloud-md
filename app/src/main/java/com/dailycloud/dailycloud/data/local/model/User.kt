package com.dailycloud.dailycloud.data.local.model

import com.google.firebase.Timestamp

data class User(
    val uid: String,
    val name: String,
    val email: String,
    val birthday: Timestamp,
    val imageUrl: String,
)