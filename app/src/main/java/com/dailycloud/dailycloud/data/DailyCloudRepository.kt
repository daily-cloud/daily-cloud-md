package com.dailycloud.dailycloud.data

import android.util.Log
import com.dailycloud.dailycloud.data.local.datastore.DataStoreManager
import com.dailycloud.dailycloud.data.local.model.Content
import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.local.model.dummy.ContentData
import com.dailycloud.dailycloud.data.remote.response.AddJournalResponse
import com.dailycloud.dailycloud.data.remote.response.AddUserResponse
import com.dailycloud.dailycloud.data.remote.response.JournalResponse
import com.dailycloud.dailycloud.data.remote.response.JournalsResponse
import com.dailycloud.dailycloud.data.remote.response.UpdateUserResponse
import com.dailycloud.dailycloud.data.remote.response.QuotesResponse
import com.dailycloud.dailycloud.data.remote.response.TodayJournalResponse
import com.dailycloud.dailycloud.data.remote.response.UploadImageResponse
import com.dailycloud.dailycloud.data.remote.response.UserDetailResponse
import com.dailycloud.dailycloud.data.remote.service.ApiService
import com.dailycloud.dailycloud.data.remote.service.AuthService
import com.dailycloud.dailycloud.data.remote.service.JournalService
import com.dailycloud.dailycloud.ui.common.UiState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.tasks.await
import okhttp3.MultipartBody
import javax.inject.Inject

class DailyCloudRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val apiService: ApiService,
    private val googleSignInRequest: BeginSignInRequest,
    private val oneTapClient: SignInClient,
    private val googleClient: GoogleSignInClient,
    private val dataStoreManager: DataStoreManager
) :
    AuthService,
    JournalService
{

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override val userToken: Flow<String>
        get() = dataStoreManager.getToken()

    suspend fun saveToken(token: String) {
        dataStoreManager.setToken(token)
    }

    val todayActivity: Flow<String>
        get() = dataStoreManager.getTodayActivity()

    suspend fun saveTodayActivity(activity: String) {
        dataStoreManager.setTodayActivity(activity)

    suspend fun getDetailUser() : Flow<UiState<UserDetailResponse>> = flow{
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.getUserDetails("Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun login(email: String, password: String): Flow<UiState<AuthResult>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(auth.signInWithEmailAndPassword(email, password).await()))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun loginWithGoogle(googleCredential: AuthCredential): Flow<UiState<AuthResult>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(auth.signInWithCredential(googleCredential).await()))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun oneTapSignIn(): Flow<UiState<BeginSignInResult>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(oneTapClient.beginSignIn(googleSignInRequest).await()))
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

    suspend fun addUser(
        email: String,
        name: String,
        birthday: String?,
    ): Flow<UiState<AddUserResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.addUser(email, name, birthday ?: "01-01-2001", "Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun updateUser(
        name: String,
    ): Flow<UiState<UpdateUserResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.updateUser(name, "Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun verifyEmail(email: String) {
        auth.currentUser?.sendEmailVerification()?.await()
    }

    override suspend fun logout() {
        auth.signOut()
        googleClient.signOut()
        dataStoreManager.setToken("")
    }

    private val contents = mutableListOf<Content>()
    fun getContents(): Flow<List<Content>> {
        if (contents.isEmpty()) {
            ContentData.listContent.forEach {
                contents.add(it)
            }
        }
        return flowOf(contents)
    }

    fun getContentById(contentId: String): Content {
        return contents.first {
            it.contentId == contentId
        }
    }

    override suspend fun getJournals(month: Int, year: Int): Flow<UiState<JournalsResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.getJournals(month, year, "Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun getJournal(id: String): Flow<UiState<JournalResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.getJournal(id, "Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun getTodayJournal(): Flow<UiState<TodayJournalResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.getTodayJournal("Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun addJournal(
        activity: String,
        content: String,
        mood: String,
    ): Flow<UiState<AddJournalResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.addJournal(activity, content, mood, "Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun uploadImage(image: MultipartBody.Part): Flow<UiState<UploadImageResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.uploadImage(image, "Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }

    suspend fun getQuote(): Flow<UiState<QuotesResponse>> = flow {
        emit(UiState.Loading)
        try {
            emit(UiState.Success(apiService.getQuote("Bearer ${userToken.first()}")))
        } catch (e: Exception) {
            emit(UiState.Error(e.message ?: "An unknown error occurred"))
        }
    }
}