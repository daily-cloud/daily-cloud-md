package com.dailycloud.dailycloud.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dailycloud.dailycloud.R
import com.dailycloud.dailycloud.ui.common.Activity
import com.dailycloud.dailycloud.ui.theme.DailyCloudTheme
import com.dailycloud.dailycloud.ui.theme.Primary

@Composable
fun ActivitySelect(
    modifier: Modifier = Modifier,
    activitySelected: Activity? = null,
    onActivitySelected: (Activity) -> Unit,
    isCustomActivity: Boolean,
    isCustomActivityFinished: Boolean,
    customActivity: String,
    onCustomActivityChanged: (String) -> Unit,
    onCustomFinished: (String) -> Unit,
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        val activities = listOf(
            Activity.SleepActivity,
            Activity.LearningActivity,
            Activity.PlayingActivity,
            Activity.OtherActivity
        )
        Text(
            stringResource(R.string.what_is_your_today_activity),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        if (isCustomActivity) {
            CustomActivitySelect(
                onFinished = onCustomFinished,
                customActivity = customActivity,
                onCustomActivityChanged = onCustomActivityChanged,
                isCustomActivityFinished = isCustomActivityFinished
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = rememberLazyListState(),
            ) {
                items(activities) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .background(
                                if (activitySelected?.title == it.title) Primary else Color.White,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                onActivitySelected(it)
                            }
                            .padding(vertical = 8.dp, horizontal = 10.dp)
                    ) {
                        Text(
                            it.title,
                            color = if (activitySelected?.title == it.title) Color.White else Color.Black,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomActivitySelect(
    onFinished: (String) -> Unit,
    customActivity: String,
    onCustomActivityChanged: (String) -> Unit,
    isCustomActivityFinished: Boolean,
) {
    BasicTextField(
        value = customActivity,
        onValueChange = onCustomActivityChanged,
        singleLine = true,
        enabled = !isCustomActivityFinished,
        modifier = Modifier
            .padding(16.dp)
            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp, horizontal = 10.dp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                innerTextField()
                if (!isCustomActivityFinished) {
                    IconButton(
                        onClick = {
                            onFinished(customActivity)
                        },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null)
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun ActivityPreview() {
    DailyCloudTheme() {
        Surface(modifier = Modifier.fillMaxWidth()) {
            ActivitySelect(
                onActivitySelected = {},
                isCustomActivity = false,
                isCustomActivityFinished = false,
                customActivity = "",
                onCustomActivityChanged = {},
                onCustomFinished = {}
            )
        }
    }
}