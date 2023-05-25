package com.dailycloud.dailycloud.data

import android.util.Log
import com.dailycloud.dailycloud.data.local.model.User
import com.dailycloud.dailycloud.data.remote.service.ApiService
import com.dailycloud.dailycloud.data.remote.service.AuthService
import com.dailycloud.dailycloud.ui.common.UiState
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DailyCloudRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val apiService: ApiService,
) :
    AuthService,
{

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun login(email: String, password: String): Flow<UiState<AuthResult>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(auth.signInWithEmailAndPassword(email, password).await()))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        birthday: Timestamp?,
        imageUrl: String?
    ): Flow<UiState<AuthResult>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(auth.createUserWithEmailAndPassword(email, password).await()))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun verifyEmail(email: String) {
        auth.currentUser?.sendEmailVerification()?.await()
    }

    override suspend fun logout() {
        auth.signOut()
    }

}