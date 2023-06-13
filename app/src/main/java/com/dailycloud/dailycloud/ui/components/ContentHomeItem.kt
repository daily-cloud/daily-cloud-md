package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ContentHomeItem(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    onClick: () -> Unit
) {
    Box(modifier = modifier.clip(RoundedCornerShape(16.dp)).width(173.dp).height(105.dp).clickable(onClick = onClick).background(Color.Black)) {
        AsyncImage(model = image, contentDescription = title, contentScale = ContentScale.Crop, modifier = Modifier.alpha(0.5f))
        Text(
            title,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Center).padding(24.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.White
        )
    }
}
