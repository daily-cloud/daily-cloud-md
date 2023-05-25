package com.dailycloud.dailycloud.data.local.model

import com.google.firebase.Timestamp

data class Journal(
    val journalId: String,
    val content: String,
    val date: Timestamp,
    val createdAt: Timestamp,
    val prediction: Boolean,
    val mood: String,
    val userId: String,
)