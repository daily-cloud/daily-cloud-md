package com.dailycloud.dailycloud.ui.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.dailycloud.dailycloud.ui.components.CustomFilledButton

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    toHome: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Coming Soon")
        CustomFilledButton(onClick = { toHome() }, text = "Back to Home")
    }
}