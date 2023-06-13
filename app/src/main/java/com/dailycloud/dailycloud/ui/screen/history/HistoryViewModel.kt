package com.dailycloud.dailycloud.ui.screen.history

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.remote.response.JournalsResponse
import com.dailycloud.dailycloud.ui.common.UiState
import com.dailycloud.dailycloud.util.Util.toDay
import com.dailycloud.dailycloud.util.Util.toMonth
import com.dailycloud.dailycloud.util.Util.toYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<JournalsResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<JournalsResponse>>
        get() = _uiState

    private val _date: MutableState<Date> = mutableStateOf(Date())
    val date: State<Date> = _date

    private val _selectedDateJournal: MutableState<Journal?> = mutableStateOf(null)
    val selectedDateJournal: State<Journal?> = _selectedDateJournal

    private val _currentJournals: MutableState<List<Journal?>> = mutableStateOf(listOf())
    val currentJournals: State<List<Journal?>> = _currentJournals

    @OptIn(ExperimentalMaterial3Api::class)
    private val _datePickerState: DatePickerState = DatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayedMonthMillis = null,
        yearRange = (2023..2030),
        initialDisplayMode = DisplayMode.Picker,
    )
    @OptIn(ExperimentalMaterial3Api::class)
    val datePickerState: DatePickerState = _datePickerState

    init {
        getJournals(System.currentTimeMillis())
        _selectedDateJournal.value = currentJournals.value.find {
            it?.date!!.toDay() == System.currentTimeMillis().toDay()
        }
    }

    private fun getJournals(date: Long) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.getJournals(date.toMonth(), date.toYear()).collect {
                when (it) {
                    is UiState.Loading -> { _uiState.value = it }
                    is UiState.Success -> {
                        _uiState.value = it
                        _currentJournals.value = it.data.data ?: listOf()
                    }
                    is UiState.Error -> { _uiState.value = it }
                }
            }
        }
    }

    fun onDateChanged(dateMills: Long) {
        if (_date.value.month != Date(dateMills).month || _date.value.year != Date(dateMills).year) {
            getJournals(dateMills)
        }
        _selectedDateJournal.value = currentJournals.value.find {
            it?.date!!.toDay() == dateMills.toDay()
        }
        _date.value = Date(dateMills)
    }
}