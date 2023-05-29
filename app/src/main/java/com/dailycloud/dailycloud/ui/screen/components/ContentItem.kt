package com.dailycloud.dailycloud.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme

@Composable
fun ContentListItem(
    name: String,
    articlePreview: String,
    photoUrl: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .size(60.dp)
                .clip(shape = RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
            )
            Text(
                text = articlePreview,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, end = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroListItemPreview() {
    DailyCloudTheme {
        ContentListItem(
            name = "Lorem ipsum",
            articlePreview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
            photoUrl = "https://www.themoviedb.org/t/p/w440_and_h660_face/bCXgdvCobMHYIGblzbaNMF4SnCm.jpg"
        )
    }
}