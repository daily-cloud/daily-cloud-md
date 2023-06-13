package com.dailycloud.dailycloud.ui.common

sealed class Activity(
    val title: String,
) {
    object SleepActivity: Activity("Tidur")
    object LearningActivity: Activity("Belajar")
    object PlayingActivity: Activity("Bermain")
    object OtherActivity: Activity("Lainnya")
}
