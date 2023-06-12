package com.dailycloud.dailycloud.ui.screen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.Mood
import com.dailycloud.dailycloud.ui.components.ActivitySelect
import com.dailycloud.dailycloud.ui.components.ContentHomeItem
import com.dailycloud.dailycloud.ui.components.JournalPreview
import com.dailycloud.dailycloud.ui.components.MoodChoices

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    toJournal: (String?) -> Unit,
    toContent: (String) -> Unit,
) {
    val activitySelected by viewModel.selectedActivity
    val isCustomActivity by viewModel.isCustomActivity
    val isCustomActivityFinished by viewModel.isCustomActivityFinished
    val customActivity by viewModel.customActivity
    val journalContent by viewModel.journalContent
    val contents by viewModel.contents
    val quote by viewModel.quote
    val journal by viewModel.journal
    val context = LocalContext.current

    LaunchedEffect(null) {
        viewModel.getTodayJournal()
    }

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
        JournalPreview(journalContent = journalContent) {
            if (activitySelected == null) {
                showToast(context, "Please select activity first")
            } else {
                toJournal(journal?.journalId)
            }
        }
        Text("For You", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 16.dp))
        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = rememberLazyListState(),
        ) {
            items(contents) {
                ContentHomeItem(
                    title = it.title,
                    image = it.photoPath
                ) {
                    toContent(it.contentId)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            quote ?: stringResource(id = R.string.content_quote),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        )
    }

}

private fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}