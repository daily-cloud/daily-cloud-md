package com.dailycloud.dailycloud.di

import com.dailycloud.dailycloud.data.DailyCloudRepository
import com.dailycloud.dailycloud.data.remote.service.ApiService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideRepository(
        auth: FirebaseAuth,
        apiService: ApiService,
    ) = DailyCloudRepository(
        auth,
        apiService,
    )

}