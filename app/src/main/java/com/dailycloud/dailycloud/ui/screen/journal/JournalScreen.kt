package com.dailycloud.dailycloud.ui.screen.journal

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.data.remote.response.AddJournalResponse
import com.dailycloud.dailycloud.data.remote.response.AddUserResponse
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary
import com.dailycloud.dailycloud.util.Util
import com.google.firebase.Timestamp
import java.util.Date

@Composable
fun JournalScreen(
    modifier: Modifier = Modifier,
    viewModel: JournalViewModel = hiltViewModel(),
    toHome: () -> Unit,
    toCamera: () -> Unit,
    toResult: (AddJournalResponse) -> Unit,
    mood: String?,
    journalId: String? = null
) {
    LaunchedEffect(null) {
        Log.d("JournalScreen", "journalId: $journalId")
        if (journalId != "{journalId}") {
            viewModel.getJournal(journalId!!)
        }
    }
    val content by viewModel.journalContent
    val enabled by viewModel.journalContentEnabled
    val journal by viewModel.journal
    val isJournalSubmitted by viewModel.isJournalSubmitted

    Column(modifier = modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.clickable { toHome() },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(stringResource(R.string.daily_story), style = MaterialTheme.typography.displaySmall)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Primary, shape = RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
             if (journalId != "{journalId}") {
                 Text("Date : ${Util.dateFormat(Timestamp(journal?.date?.seconds ?: 0, journal?.date?.nanoseconds ?: 0))}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                 Text("Mood : ${journal?.mood}", style = MaterialTheme.typography.titleMedium, color = Color.White)
                 Text("Prediction : ${if (journal?.prediction?.depression == true) "Depress Detected" else "Depress Not Detected"}", style = MaterialTheme.typography.titleMedium, color = Color.White)
             } else {
                 Text("Date : ${Util.dateFormat(Timestamp.now())}", style = MaterialTheme.typography.titleMedium, color = Color.White)
             }
        }
        Spacer(modifier = Modifier.height(16.dp))
        BasicTextField(
            value = content,
            onValueChange = viewModel::onJournalContentChanged,
            enabled = enabled,
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                Box() {
                    if (content.isEmpty()) {
                        Text(
                            text = "Write your journal here",
                            style = TextStyle(color = Color.Gray),
                        )
                    }
                }
                innerTextField()
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
        if (journalId == "{journalId}") {
            CustomFilledButton(
                onClick = {
                    if (mood != null) {
                        viewModel.onJournalSubmitted(toResult, mood)
                    } else {
                        toCamera()
                    }
                },
                modifier = Modifier.align(Alignment.End),
                text = if (mood != null) stringResource(R.string.next) else "Take Picture",
                enabled = !isJournalSubmitted
            )
        }
    }
}
