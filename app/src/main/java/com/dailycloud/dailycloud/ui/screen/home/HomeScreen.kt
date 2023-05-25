package com.dailycloud.dailycloud.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.Mood
import com.dailycloud.dailycloud.ui.components.JournalPreview
import com.dailycloud.dailycloud.ui.components.MoodChoices

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    toJournal: () -> Unit,
) {
    val isSelected by viewModel.isMoodSelected
    val moodSelected by viewModel.selectedMood
    val moodHistory by viewModel.moodHistory
    val journalContent by viewModel.journalContent

    Column(modifier = modifier) {
        Text("Hello, Cloudie!", style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(16.dp))
        MoodChoices(isSelected = isSelected, moodSelected = moodSelected, moodHistory = moodHistory, onMoodSelected = viewModel::onMoodSelected)
        JournalPreview(journalContent = journalContent, toJournal = toJournal)
    }

}