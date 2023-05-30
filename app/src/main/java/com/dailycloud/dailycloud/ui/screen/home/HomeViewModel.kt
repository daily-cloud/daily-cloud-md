package com.dailycloud.dailycloud.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.common.Activity
import com.dailycloud.dailycloud.ui.common.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _selectedActivity: MutableState<Activity?> = mutableStateOf(null)
    val selectedActivity: State<Activity?> = _selectedActivity

    private val _isCustomActivity: MutableState<Boolean> = mutableStateOf(false)
    val isCustomActivity: State<Boolean> = _isCustomActivity

    private val _isCustomActivityFinished: MutableState<Boolean> = mutableStateOf(false)
    val isCustomActivityFinished: State<Boolean> = _isCustomActivityFinished

    private val _customActivity: MutableState<String> = mutableStateOf("")
    val customActivity: State<String> = _customActivity

    private val _journalContent: MutableState<String?> = mutableStateOf(null)
    val journalContent: State<String?> = _journalContent

    fun onActivitySelected(activity: Activity) {
        if (_selectedActivity.value == null) {
            _selectedActivity.value = activity
            if (activity is Activity.OtherActivity) _isCustomActivity.value = true
        }
    }

    fun onCustomActivityChanged(customActivity: String) {
        _customActivity.value = customActivity
    }

    fun onCustomFinished(customActivity: String) {
        _isCustomActivityFinished.value = true
    }

}