package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme

@Composable
fun ContentHomeItem(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    onClick: () -> Unit
) {
    Box(modifier = modifier.clip(RoundedCornerShape(16.dp)).width(120.dp).height(72.dp).clickable(onClick = onClick)) {
        AsyncImage(model = image, contentDescription = title, alpha = 0.7f)
        Text(title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.align(Alignment.Center), maxLines = 1)
    }
}
