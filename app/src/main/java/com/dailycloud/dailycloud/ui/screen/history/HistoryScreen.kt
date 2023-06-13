package com.dailycloud.dailycloud.ui.screen.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary
import com.dailycloud.dailycloud.util.Util.toDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    toJournal: (String) -> Unit,
) {
    val datePickerState = viewModel.datePickerState
    val selectedDateJournal by viewModel.selectedDateJournal
    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        viewModel.onDateChanged(datePickerState.selectedDateMillis ?: System.currentTimeMillis())
    }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.history), style = MaterialTheme.typography.displaySmall)
        Text(
            stringResource(R.string.track_your_activities),
            style = MaterialTheme.typography.titleLarge
        )
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            ),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                colors = DatePickerDefaults.colors(
                    selectedDayContainerColor = Primary,
                    selectedDayContentColor = Color.White,
                    selectedYearContainerColor = Primary,
                    selectedYearContentColor = Color.White,
                    todayContentColor = Primary,
                    todayDateBorderColor = Primary,
                    currentYearContentColor = Primary,
                ),
                title = { },
                headline = {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.Center,
                    ) {
                        viewModel.uiState.collectAsState().value.let { state ->
                            when (state) {
                                is UiState.Loading -> Text("Loading")
                                is UiState.Success -> {
                                    Text("Tanggal: ${datePickerState.selectedDateMillis?.toDate()}", style = MaterialTheme.typography.bodyLarge)
                                    if (selectedDateJournal != null) {
                                        Text(
                                            "Perasaan: ${selectedDateJournal!!.mood}",
                                            style = MaterialTheme.typography.bodyLarge
                                        )
                                        CustomFilledButton(
                                            onClick = { toJournal(selectedDateJournal!!.journalId) },
                                            text = "Cek Cerita",
                                            modifier = Modifier.align(Alignment.End)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))
                                }
                                is UiState.Error -> Text("Error")
                            }
                        }
                    }
                },
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}