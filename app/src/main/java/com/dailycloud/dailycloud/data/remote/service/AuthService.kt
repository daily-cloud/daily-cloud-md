package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.ui.common.UiState
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GetTokenResult
import kotlinx.coroutines.flow.Flow

interface AuthService {

    val currentUser: FirebaseUser?
    val userToken: Flow<String>
    suspend fun login(email: String, password: String): Flow<UiState<AuthResult>>
    suspend fun loginWithGoogle(googleCredential: AuthCredential): Flow<UiState<AuthResult>>
    suspend fun register(email: String, password: String, name: String, birthday: Timestamp?, imageUrl: String?): Flow<UiState<AuthResult>>
    suspend fun verifyEmail(email: String)
    suspend fun logout()

}