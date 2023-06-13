package com.dailycloud.dailycloud.ui.screen.profile

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: DailyCloudRepository): ViewModel() {

    private val _name: MutableState<String> = mutableStateOf("")
    val name: State<String> = _name

    private val _email: MutableState<String> = mutableStateOf("")
    val email: State<String> = _email

    init {
        getUserData()
    }

    private fun getUserData() {
        viewModelScope.launch {
            repository.getDetailUser().collect{
                when (it) {
                    is UiState.Loading -> {

                    }
                    is UiState.Success -> {
                        _name.value = it.data.data!!.name!!
                        _email.value = it.data.data.email!!
                    }
                    is UiState.Error -> {
                        Log.d("ProfileViewModel", it.errorMessage)
                    }
                }
            }
        }
    }

    fun updateUser(toProfile: () -> Unit, name:String) {
        viewModelScope.launch {
            repository.updateUser(name).collect{
                when (it) {
                    is UiState.Loading -> {

                    }
                    is UiState.Success -> {
                        toProfile()
                    }
                    is UiState.Error -> {
                        Log.d("ProfileViewModel", it.errorMessage)
                    }
                }
            }
        }
    }

    fun signOut(toGetStarted: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            toGetStarted()
        }
    }
}