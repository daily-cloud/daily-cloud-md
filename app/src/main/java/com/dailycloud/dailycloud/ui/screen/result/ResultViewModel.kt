package com.dailycloud.dailycloud.ui.screen.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Journal>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Journal>>
        get() = _uiState

    fun getJournal(id: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            repository.getJournal(id).collect {
                when (it) {
                    is UiState.Loading -> _uiState.value = it
                    is UiState.Success -> {
                        _uiState.value = it.data.journal?.let { journal ->
                            UiState.Success(journal)
                        } ?: UiState.Error("Journal not found")
                    }
                    is UiState.Error -> _uiState.value = it
                }
            }
        }
    }

}