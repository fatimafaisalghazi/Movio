package com.madrid.data.repositories

import com.madrid.data.repositories.datasource.UserPreferences
import com.madrid.data.repositories.mapper.toBoolean
import com.madrid.data.repositories.mapper.toTheme
import com.madrid.domain.repository.UserPreferencesRepository
import com.madrid.domain.utils.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class UserPreferencesRepositoryImpl(
    private val userPreferences: UserPreferences
) : UserPreferencesRepository {
    override fun getAppDarkModeOn(): Flow<Theme> {
        return userPreferences.getAppDarkModeOn().toTheme()
    }

    override suspend fun setAppDarkModeOn(theme: Theme) {
        userPreferences.setAppDarkModeOn(theme.toBoolean())
    }

    override suspend fun getAppLanguage(): String {
        return userPreferences.getAppLanguage().first().toString()
    }

    override suspend fun setAppLanguage(language: String) {
        userPreferences.setAppLanguage(language)
    }
}