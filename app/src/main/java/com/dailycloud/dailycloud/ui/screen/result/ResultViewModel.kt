package com.dailycloud.dailycloud.ui.screen.result

import androidx.lifecycle.ViewModel
import com.dailycloud.dailycloud.data.DailyCloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {
}