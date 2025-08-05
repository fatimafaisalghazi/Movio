package com.madrid.data.repositories.datasource

import kotlinx.coroutines.flow.Flow

interface AuthenticationDataSource {
    fun getAuthToken(): Flow<String> //get session id
    fun isUserLoggedIn(): Flow<Boolean>
    suspend fun setAuthToken(token: String)
    suspend fun clearAuthToken()
}