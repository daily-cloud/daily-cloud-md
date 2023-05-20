package com.dailycloud.dailycloud.ui.screen.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.common.UiState
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: DailyCloudRepository) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> get() = _confirmPassword

    private val _name = mutableStateOf("")
    val name: State<String> get() = _name

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
    }

    fun onNameChanged(name: String) {
        _name.value = name
    }

    fun signUp() {
        viewModelScope.launch {
            repository.register(_email.value, _password.value, _name.value, null, null)
                .collect {
                    when (it) {
                        is UiState.Loading -> {

                        }
                        is UiState.Success -> {

                        }
                        is UiState.Error -> {

                        }
                    }
                }
        }
    }
}