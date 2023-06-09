package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.remote.response.AddUserResponse
import com.dailycloud.dailycloud.data.remote.response.JournalsResponse
import com.dailycloud.dailycloud.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface JournalService {
    suspend fun getJournals(): Flow<UiState<JournalsResponse>>
    suspend fun getJournal(id: String): Flow<UiState<Journal>>
    suspend fun addJournal(title: String, content: String, mood: String, prediction: String): Flow<UiState<AddUserResponse>>
}