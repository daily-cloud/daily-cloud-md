package com.dailycloud.dailycloud.util

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

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
}