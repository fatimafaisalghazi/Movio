package com.madrid.data.repositories.mapper

import com.madrid.domain.utils.AppLanguage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<String>.toLanguage() = this.map { language ->
    if (language == "en") AppLanguage.ENGLISH else AppLanguage.ARABIC
}

fun AppLanguage.toStringLanguage() = if (this == AppLanguage.ENGLISH) "en" else "ar"