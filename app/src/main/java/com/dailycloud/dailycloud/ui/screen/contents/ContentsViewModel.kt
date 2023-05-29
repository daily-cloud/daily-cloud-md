package com.dailycloud.dailycloud.ui.screen.contents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentsViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel()
{
    private val _uiState: MutableStateFlow<UiState<List<Content>>> = MutableStateFlow(UiState.Loading)

    val uiState: StateFlow<UiState<List<Content>>>
        get() = _uiState

    fun getContents() {
        viewModelScope.launch {
            repository.getContents()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }
}