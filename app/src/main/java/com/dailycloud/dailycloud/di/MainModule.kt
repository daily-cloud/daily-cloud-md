package com.dailycloud.dailycloud.di

import com.dailycloud.dailycloud.data.DailyCloudRepository
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
    fun provideRepository() = DailyCloudRepository()

}