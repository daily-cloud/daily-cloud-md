package com.dailycloud.dailycloud.ui.screen.content

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Content>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Content>>
        get() = _uiState

    fun getContentById(contentId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getContentById(contentId))
        }
    }
}