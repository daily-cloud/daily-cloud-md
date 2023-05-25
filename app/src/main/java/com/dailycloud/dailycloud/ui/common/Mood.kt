package com.dailycloud.dailycloud.ui.common

import androidx.compose.ui.graphics.Color
import com.dailycloud.dailycloud.R

sealed class Mood(
    val color: Color,
    val icon: Int,
    val title: String,
) {

    object HappyMood: Mood(Color(0xFFF7FA67), R.drawable.smiling, "Happy")
    object NeutralMood: Mood(Color(0xFFAEAFF7), R.drawable.smiling, "Neutral")
    object SadMood: Mood(Color(0xFFA0E3E2), R.drawable.smiling, "Sad")
    object AngryMood: Mood(Color(0xFFF09E54), R.drawable.smiling, "Angry")
    object LovingMood: Mood(Color(0xFFEF5DA8), R.drawable.smiling, "Loving")

}
