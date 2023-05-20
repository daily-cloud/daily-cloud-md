package com.dailycloud.dailycloud.ui.screen.content

import androidx.lifecycle.ViewModel
import com.dailycloud.dailycloud.data.DailyCloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {
}