package com.dailycloud.dailycloud.ui.common

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.dailycloud.dailycloud.R

sealed class Activity(
    val title: String,
) {
    object SleepActivity: Activity("Tidur")
    object LearningActivity: Activity("Belajar")
    object PlayingActivity: Activity("Bermain")
    object OtherActivity: Activity("Lainnya")
}
