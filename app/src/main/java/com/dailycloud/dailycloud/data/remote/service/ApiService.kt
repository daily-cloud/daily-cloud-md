package com.dailycloud.dailycloud.data.remote.service

import com.dailycloud.dailycloud.data.local.model.Journal
import retrofit2.http.GET

interface ApiService {

    @GET("api/journals")
    suspend fun getJournals(): List<Journal>

}