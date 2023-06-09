package com.dailycloud.dailycloud.ui.screen.journal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _journalContent: MutableState<String> = mutableStateOf("")
    val journalContent: State<String> = _journalContent

    fun onJournalContentChanged(content: String) {
        _journalContent.value = content
    }

    fun onJournalSubmitted(toCamera: () -> Unit,) {
        viewModelScope.launch {
            repository.addJournal(
                title = "title",
                content = _journalContent.value,
                mood = "mood",
                prediction = "prediction",
            ).collect {
                when (it) {
                    is UiState.Loading -> {  }
                    is UiState.Success -> { toCamera() }
                    is UiState.Error -> { Log.e("JournalViewModel", "Error: ${it.errorMessage}") }
                }
            }
        }
    }
}