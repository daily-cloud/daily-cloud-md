package com.dailycloud.dailycloud.ui.screen.journal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.remote.response.AddJournalResponse
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _journalContent: MutableState<String> = mutableStateOf("")
    val journalContent: State<String> = _journalContent

    private val _journalContentEnabled: MutableState<Boolean> = mutableStateOf(true)
    val journalContentEnabled: State<Boolean> = _journalContentEnabled

    private val _journal: MutableState<Journal?> = mutableStateOf(null)
    val journal: State<Journal?> = _journal

    private val _isJournalSubmitted: MutableState<Boolean> = mutableStateOf(false)
    val isJournalSubmitted: State<Boolean> = _isJournalSubmitted

    fun onJournalContentChanged(content: String) {
        _journalContent.value = content
    }

    fun onJournalSubmitted(toResult: (AddJournalResponse) -> Unit, mood: String) {
        _isJournalSubmitted.value = true
        viewModelScope.launch {
            repository.addJournal(
                activity = repository.todayActivity.first(),
                content = _journalContent.value,
                mood = mood,
            ).collect {
                when (it) {
                    is UiState.Loading -> {  }
                    is UiState.Success -> {
                        toResult(it.data)
                        _isJournalSubmitted.value = false
                    }
                    is UiState.Error -> {
                        Log.e("JournalViewModel", "Error: ${it.errorMessage}")
                        _isJournalSubmitted.value = false
                    }
                }
            }
        }
    }

    fun getJournal(id: String) {
        viewModelScope.launch {
            repository.getJournal(id).collect {
                when (it) {
                    is UiState.Loading -> {  }
                    is UiState.Success -> {
                        Log.d("JournalViewModel", "Success: ${it.data}")
                        _journal.value = it.data.journal
                        _journalContent.value = it.data.journal?.content ?: "An Error Occurred"
                        _journalContentEnabled.value = false
                    }
                    is UiState.Error -> {
                        Log.e("JournalViewModel", "Error: ${it.errorMessage}") }
                }
            }
        }
    }
}