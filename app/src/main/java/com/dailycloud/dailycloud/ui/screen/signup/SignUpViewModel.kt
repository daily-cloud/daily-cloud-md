package com.dailycloud.dailycloud.ui.screen.signup

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.ui.common.UiState
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: DailyCloudRepository, val oneTapClient: SignInClient) : ViewModel() {

    private val _isSignUpWithEmail = MutableStateFlow(false)
    val isSignUpWithEmail: StateFlow<Boolean> get() = _isSignUpWithEmail

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _confirmPassword = mutableStateOf("")
    val confirmPassword: State<String> get() = _confirmPassword

    private val _name = mutableStateOf("")
    val name: State<String> get() = _name

    private val _isAgree = mutableStateOf(false)
    val isAgree: State<Boolean> get() = _isAgree

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

    fun onAgreeChanged(isAgree: Boolean) {
        _isAgree.value = isAgree
    }

    fun toSignUpWithEmail() {
        _isSignUpWithEmail.value = true
    }

    fun signUp(toHome: () -> Unit) {
        viewModelScope.launch {
//            repository.addUser("hNOzp5pAcBgZUWqpldUWgG2PShk1", _email.value, _email.value, Timestamp.now())
            repository.register(_email.value, _password.value, _name.value, null, null)
                .collect {
                    when (it) {
                        is UiState.Loading -> {

                        }
                        is UiState.Success -> {
                            it.data.user?.getIdToken(true)?.addOnSuccessListener { result ->
                                viewModelScope.launch {
                                    repository.saveToken(result.token!!)
                                    repository.addUser(it.data.user!!.uid, _email.value, _email.value, Timestamp.now())
                                    toHome()
                                }
                            }
                        }
                        is UiState.Error -> {
                            Log.d("SignUpViewModel", it.errorMessage)
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