package com.dailycloud.dailycloud.ui.screen.journal

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dailycloud.dailycloud.data.DailyCloudRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _journalContent: MutableState<String> = mutableStateOf("")
    val journalContent: State<String> = _journalContent

    fun onJournalContentChanged(content: String) {
        _journalContent.value = content
    }
}