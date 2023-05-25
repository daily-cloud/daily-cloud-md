package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.Mood
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme

@Composable
fun MoodChoices(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    moodSelected: Mood? = null,
    moodHistory: List<Mood>? = null,
    onMoodSelected: (Mood) -> Unit,
) {
    if (isSelected && moodSelected != null && moodHistory != null) {
        MoodSelected(modifier, moodSelected, moodHistory)
    } else {
        MoodNotSelected(modifier, onMoodSelected)
    }
}

@Composable
fun MoodSelected(
    modifier: Modifier = Modifier,
    moodSelected: Mood,
    moodHistory: List<Mood>,
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxWidth(0.5f)
                .background(color = moodSelected.color, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.BottomEnd,
        ) {
            Image(
                painter = painterResource(moodSelected.icon),
                contentDescription = moodSelected.title,
                modifier = Modifier
                    .size(128.dp)
                    .offset(x = 16.dp)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(stringResource(R.string.you_re_feeling_today, moodSelected.title.lowercase()), style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(stringResource(R.string.your_last_3_moods), style = MaterialTheme.typography.titleMedium)
            LazyRow(
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                state = rememberLazyListState(),
            ) {
                items(moodHistory) {
                    MoodChoice(it)
                }
            }
        }
    }
}

@Composable
fun MoodNotSelected(
    modifier: Modifier = Modifier,
    onMoodSelected: (Mood) -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        val moods = listOf(Mood.HappyMood, Mood.NeutralMood, Mood.SadMood, Mood.AngryMood, Mood.LovingMood)
        Text(stringResource(R.string.how_are_you_feeling_today), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(horizontal = 16.dp))
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = rememberLazyListState(),
        ) {
            items(moods) {
                MoodChoice(it, onMoodSelected = onMoodSelected)
            }
        }
    }
}

@Composable
fun MoodChoice(mood: Mood, onMoodSelected: (Mood) -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clickable { onMoodSelected(mood) }
                .background(color = mood.color, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(mood.icon),
                contentDescription = mood.title,
                modifier = Modifier.padding(8.dp)
            )
        }
        Text(mood.title, style = MaterialTheme.typography.labelLarge)
    }
}