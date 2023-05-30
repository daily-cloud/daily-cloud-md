package com.dailycloud.dailycloud.ui.screen.history

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
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
import com.dailycloud.dailycloud.ui.components.CustomFilledButton
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary
import com.dailycloud.dailycloud.util.Util.toDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
    toJournal: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(stringResource(R.string.history), style = MaterialTheme.typography.displaySmall)
        Text(stringResource(R.string.history), style = MaterialTheme.typography.titleLarge)
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
            title = {  },
            headline = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Tanggal: ${datePickerState.selectedDateMillis?.toDate()}", style = MaterialTheme.typography.bodyLarge)
                    Text("Perasaan: ", style = MaterialTheme.typography.bodyLarge)
                    CustomFilledButton(onClick = { toJournal() }, text = "Cek Cerita", modifier = Modifier.align(Alignment.End))
                    Spacer(modifier = Modifier.height(16.dp))
                }
            },
        )

    }
}