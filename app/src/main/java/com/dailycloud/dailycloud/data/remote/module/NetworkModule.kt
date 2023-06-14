package com.dailycloud.dailycloud.data.remote.module

import com.dailycloud.dailycloud.data.remote.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun apiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://daily-cloud-e5zoegfyha-et.a.run.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}