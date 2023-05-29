package com.dailycloud.dailycloud.data.local.model

import com.google.firebase.Timestamp

data class Content(
    val contentId: String,
    val photoPath: String,
    val title: String,
    val article: String,
    val author: String,
    val createdAt: Timestamp
)