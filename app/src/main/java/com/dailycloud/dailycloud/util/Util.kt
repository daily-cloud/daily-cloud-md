package com.dailycloud.dailycloud.util

object Util {
    fun reduceText(text : String, maxLength : Int) : String{

        return if (text.length > maxLength) {
             text.substring(0, maxLength) + "..."
        } else {
            text
        }
    }
}