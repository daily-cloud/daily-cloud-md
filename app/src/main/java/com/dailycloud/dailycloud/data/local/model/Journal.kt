package com.dailycloud.dailycloud.data.local.model

import com.dailycloud.dailycloud.util.Util.toDate
import com.google.firebase.Timestamp
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Journal(
    val journalId: String,
    val activity: String,
    val content: String,
    val date: Date,
    val prediction: Prediction,
    val mood: String,
    val userId: String,
)

data class Prediction(
    val depression: Boolean,
    val confidenceScore: Double,
)

data class Date(
    @field:SerializedName("_seconds")
    val seconds: Long,
    @field:SerializedName("_nanoseconds")
    val nanoseconds: Int,
) {
    fun toTimestamp(): Timestamp {
        return Timestamp(seconds, nanoseconds)
    }

    fun toDate(): java.util.Date {
        return Timestamp(seconds, nanoseconds).toDate()
    }

    fun toDay(): Int {
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        return dateFormat.format(Timestamp(seconds, nanoseconds).toDate()).toInt()
    }
}