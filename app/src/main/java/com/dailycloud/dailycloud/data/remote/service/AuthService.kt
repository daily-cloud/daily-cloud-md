package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.data.local.model.User
import com.dailycloud.dailycloud.ui.common.UiState
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthService {

    val currentUser: FirebaseUser?

    suspend fun login(email: String, password: String): Flow<UiState<AuthResult>>
    suspend fun register(email: String, password: String, name: String, birthday: Timestamp?, imageUrl: String?): Flow<UiState<AuthResult>>
    suspend fun verifyEmail(email: String)
    suspend fun logout()

}