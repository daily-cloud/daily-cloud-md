package com.dailycloud.dailycloud.util

import android.content.res.Resources
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun reduceText(text : String, maxLength : Int) : String{

        return if (text.length > maxLength) {
             text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }

    fun dateFormat(timestamp: Timestamp): String {
        val currentDate = timestamp.toDate()
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")

        return dateFormat.format(currentDate)

    }

    fun Long.toDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return dateFormat.format(Date(this))
    }

    fun Int.dpToPx(): Int {
        val scale = Resources.getSystem().displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}

