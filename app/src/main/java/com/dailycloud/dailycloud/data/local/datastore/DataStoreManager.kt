package com.dailycloud.dailycloud.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private val tokenPref = stringPreferencesKey("token")
    private val todayActivity = stringPreferencesKey("today_activity")

    suspend fun setToken(token: String) {
        dataStore.edit { preferences ->
            preferences[tokenPref] = token
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[tokenPref] ?: ""
        }
    }

    suspend fun setTodayActivity(activity: String) {
        dataStore.edit { preferences ->
            preferences[todayActivity] = activity
        }
    }

    fun getTodayActivity(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[todayActivity] ?: ""
        }
    }
}