package com.madrid.data.repositories.datasource

import kotlinx.coroutines.flow.Flow

interface UserPreferences {
    fun getAuthToken(): Flow<String>
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun setAuthToken(token: String)
    suspend fun clearAuthToken()

    fun getAppDarkModeOn(): Flow<Boolean>
    suspend fun setAppDarkModeOn(isDarkMode: Boolean)

    fun getAppLanguage(): Flow<String>
    suspend fun setAppLanguage(language: String)
}