package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.data.local.model.Journal
import com.dailycloud.dailycloud.data.remote.response.AddJournalResponse
import com.dailycloud.dailycloud.data.remote.response.AddUserResponse
import com.dailycloud.dailycloud.data.remote.response.JournalResponse
import com.dailycloud.dailycloud.data.remote.response.JournalsResponse
import com.dailycloud.dailycloud.data.remote.response.TodayJournalResponse
import com.dailycloud.dailycloud.ui.common.UiState
import kotlinx.coroutines.flow.Flow

interface JournalService {
    suspend fun getJournals(month: Int, year: Int): Flow<UiState<JournalsResponse>>
    suspend fun getJournal(id: String): Flow<UiState<JournalResponse>>
    suspend fun getTodayJournal(): Flow<UiState<TodayJournalResponse>>
    suspend fun addJournal(activity: String, content: String, mood: String): Flow<UiState<AddJournalResponse>>
}