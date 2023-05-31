package com.dailycloud.dailycloud.ui.screen.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.local.model.Content
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
        repository.currentUser?.let {
            _name.value = it.displayName ?: it.uid
            _email.value = it.email ?: "Error occurred"
        }
    }

    fun signOut(toGetStarted: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            toGetStarted()
        }
    }
}