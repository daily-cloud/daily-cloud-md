package com.dailycloud.dailycloud.ui.screen.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.ui.common.Activity
import com.dailycloud.dailycloud.ui.common.Mood
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
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

    private val _contents: MutableState<List<Content>> = mutableStateOf(listOf())
    val contents: State<List<Content>> = _contents

    private val _journal: MutableState<Journal?> = mutableStateOf(null)
    val journal: State<Journal?> = _journal

    private val _quote: MutableState<String?> = mutableStateOf(null)
    val quote: State<String?> = _quote

    init {
//        getTodayJournal()
        getContents()
        getQuote()
    }

    fun onActivitySelected(activity: Activity) {
        if (_selectedActivity.value == null) {
            _selectedActivity.value = activity
            if (activity is Activity.OtherActivity) _isCustomActivity.value = true
            viewModelScope.launch { repository.saveTodayActivity(activity.title) }
        }
    }

    fun onCustomActivityChanged(customActivity: String) {
        _customActivity.value = customActivity
    }

    fun onCustomFinished(customActivity: String) {
        _isCustomActivityFinished.value = true
        viewModelScope.launch { repository.saveTodayActivity(customActivity) }
    }

    fun getTodayJournal() {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Token: ${repository.userToken.first()}")
            repository.getTodayJournal().collect {
                when (it) {
                    is UiState.Loading -> {
                        _journalContent.value = "Loading..."
                    }
                    is UiState.Success -> {
                        Log.d("HomeViewModel", "getTodayJournal: ${it.data}")
                        if (it.data.hasUploadedJournal?.status!!) {
                            _journal.value = it.data.hasUploadedJournal.journal
                            _journalContent.value = it.data.hasUploadedJournal.journal?.content
                            _selectedActivity.value = Activity.OtherActivity
                            _isCustomActivity.value = true
                            _isCustomActivityFinished.value = true
                            _customActivity.value = it.data.hasUploadedJournal.journal?.activity ?: "Lainnya"
                            repository.saveTodayActivity(it.data.hasUploadedJournal.journal?.activity ?: "Lainnya")
                        } else {
                            _journalContent.value = null
                            repository.saveTodayActivity("")
                        }
                    }
                    is UiState.Error -> {
                        Log.e("HomeViewModel", "getTodayJournal: ${it.errorMessage}")
                    }
                }
            }
        }
    }

    private fun getContents() {
        viewModelScope.launch {
            repository.getContents()
                .collect { contents ->
                    _contents.value = contents
                }
        }
    }

    private fun getQuote() {
        viewModelScope.launch {
            repository.getQuote()
                .collect {
                    when (it) {
                        is UiState.Loading -> {
                            _quote.value = "Loading..."
                        }
                        is UiState.Success -> {
                            _quote.value = it.data.quote?.quote ?: ""
                        }
                        is UiState.Error -> {
                            _quote.value = null
                            Log.e("HomeViewModel", "getQuote: ${it.errorMessage}")
                        }
                    }
                }
        }
    }
}