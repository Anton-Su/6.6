package com.example.a66.data.remote

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.tokenDataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_tokens")

class TokenManager(private val context: Context) {

    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")

    suspend fun saveAccessToken(token: String) {
        context.tokenDataStore.edit { it[ACCESS_TOKEN] = token }
    }

    suspend fun saveRefreshToken(token: String) {
        context.tokenDataStore.edit { it[REFRESH_TOKEN] = token }
    }

    val accessTokenFlow: Flow<String?> = context.tokenDataStore.data.map { it[ACCESS_TOKEN] }
    val refreshTokenFlow: Flow<String?> = context.tokenDataStore.data.map { it[REFRESH_TOKEN] }

    suspend fun clearTokens() {
        context.tokenDataStore.edit { it.clear() }
    }
}