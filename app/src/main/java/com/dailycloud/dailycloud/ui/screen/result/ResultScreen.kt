package com.dailycloud.dailycloud.ui.screen.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.local.model.Prediction
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.google.firebase.Timestamp

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    toHome: () -> Unit,
    viewModel: ResultViewModel = hiltViewModel(),
    id: String,
) {
    viewModel.uiState.collectAsState().value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getJournal(id)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                ResultContent(uiState.data, toHome)
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = uiState.errorMessage)
                }
            }
        }
    }
}

@Composable
fun ResultContent(journal: Journal, toHome: () -> Unit,) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(painter = if (journal.mood.lowercase() == "happy") painterResource(R.drawable.smiling) else painterResource(R.drawable.sad), contentDescription = "Sad", modifier = Modifier.size(160.dp))
        Text(text = "You looks ${journal.mood} today", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Text(text = if (journal.prediction.depression) stringResource(R.string.journal_detected) else stringResource(R.string.journal_not_detected), style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        CustomFilledButton(onClick = { toHome() }, text = "Back to Home")
    }
}