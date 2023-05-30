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
import com.dailycloud.dailycloud.ui.components.ActivitySelect
import com.dailycloud.dailycloud.ui.components.JournalPreview
import com.dailycloud.dailycloud.ui.components.MoodChoices

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    toJournal: () -> Unit,
) {
    val activitySelected by viewModel.selectedActivity
    val isCustomActivity by viewModel.isCustomActivity
    val isCustomActivityFinished by viewModel.isCustomActivityFinished
    val customActivity by viewModel.customActivity
    val journalContent by viewModel.journalContent

    Column(modifier = modifier) {
        Text("Hello, Cloudie!", style = MaterialTheme.typography.displaySmall, modifier = Modifier.padding(16.dp))
        ActivitySelect(
            onActivitySelected = viewModel::onActivitySelected,
            activitySelected = activitySelected,
            isCustomActivity = isCustomActivity,
            isCustomActivityFinished = isCustomActivityFinished,
            customActivity = customActivity,
            onCustomActivityChanged = viewModel::onCustomActivityChanged,
            onCustomFinished = viewModel::onCustomFinished
        )
        JournalPreview(journalContent = journalContent, toJournal = toJournal)
    }

}