package com.dailycloud.dailycloud.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.common.Mood
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _isMoodSelected: MutableState<Boolean> = mutableStateOf(false)
    val isMoodSelected: State<Boolean> = _isMoodSelected

    private val _selectedMood: MutableState<Mood?> = mutableStateOf(null)
    val selectedMood: State<Mood?> = _selectedMood

    private val _moodHistory: MutableState<List<Mood>> = mutableStateOf(emptyList())
    val moodHistory: State<List<Mood>> = _moodHistory

    private val _journalContent: MutableState<String?> = mutableStateOf(null)
    val journalContent: State<String?> = _journalContent

    fun onMoodSelected(mood: Mood) {
        _isMoodSelected.value = true
        _selectedMood.value = mood
        _moodHistory.value = listOf(Mood.HappyMood, Mood.NeutralMood, Mood.SadMood)
    }

}