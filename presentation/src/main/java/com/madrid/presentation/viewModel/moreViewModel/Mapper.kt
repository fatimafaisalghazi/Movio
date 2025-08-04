package com.madrid.presentation.viewModel.moreViewModel

import com.madrid.domain.utils.AppLanguage
import com.madrid.domain.utils.AppTheme

fun ThemeType.toAppTheme() = when(this) {
    ThemeType.LIGHT -> AppTheme.LIGHT
    ThemeType.DARK -> AppTheme.DARK
}

fun LanguageType.toAppLanguage() = when(this) {
    LanguageType.ARABIC -> AppLanguage.ARABIC
    LanguageType.ENGLISH -> AppLanguage.ENGLISH
}