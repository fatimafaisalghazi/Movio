package com.madrid.data.repositories.mapper

import com.madrid.domain.utils.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<Boolean>.toTheme() = this.map { isDarkMode ->
    if (isDarkMode) AppTheme.DARK else AppTheme.LIGHT
}

fun AppTheme.toBoolean() = this == AppTheme.DARK