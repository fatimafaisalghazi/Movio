package com.madrid.data.repositories.mapper

import com.madrid.domain.utils.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<Boolean>.toTheme() = this.map { isDarkMode ->
    if (isDarkMode) Theme.DARK else Theme.LIGHT
}

fun Theme.toBoolean() = this == Theme.DARK