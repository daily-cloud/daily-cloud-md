package com.dailycloud.dailycloud.ui.screen.login

import androidx.lifecycle.ViewModel
import com.dailycloud.dailycloud.data.DailyCloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {
}