package com.dailycloud.dailycloud.ui.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.common.UiState
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: DailyCloudRepository, val oneTapClient: SignInClient) : ViewModel() {

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    fun onEmailChanged(email: String) {
        _email.value = email
    }

    fun onPasswordChanged(password: String) {
        _password.value = password
    }

    fun login(toHome: () -> Unit) {
        viewModelScope.launch {
            repository.login(_email.value, _password.value)
                .collect {
                    when (it) {
                        is UiState.Loading -> {

                        }
                        is UiState.Success -> {
                            it.data.user?.getIdToken(true)?.addOnSuccessListener { result ->
                                viewModelScope.launch {
                                    repository.saveToken(result.token!!)
                                    toHome()
                                }
                            }
                        }
                        is UiState.Error -> {

                        }
                    }
                }
        }
    }

    fun oneTapSignIn(launch: (result: BeginSignInResult) -> Unit) = viewModelScope.launch {
        repository.oneTapSignIn().collect {
            when (it) {
                is UiState.Loading -> {

                }
                is UiState.Success -> {
                    launch(it.data)
                }
                is UiState.Error -> {

                }
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential, toHome: () -> Unit) = viewModelScope.launch {
        repository.loginWithGoogle(googleCredential).collect {
            when (it) {
                is UiState.Loading -> {

                }
                is UiState.Success -> {
                    toHome()
                }
                is UiState.Error -> {

                }
            }
        }
    }
}