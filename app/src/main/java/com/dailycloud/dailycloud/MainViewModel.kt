package com.dailycloud.dailycloud

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: DailyCloudRepository): ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Login.route)
    val startDestination: State<String> = _startDestination

    init {
        // Any Process Here

        if (repository.currentUser != null) {
            _startDestination.value = Screen.Home.route
        } else {
            _startDestination.value = Screen.GetStarted.route
        }
        _isLoading.value = false
    }

}