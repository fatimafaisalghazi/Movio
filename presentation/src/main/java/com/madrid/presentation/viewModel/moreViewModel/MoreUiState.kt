package com.madrid.presentation.viewModel.moreViewModel

data class MoreUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val profilePictureUrl: String? = null,
    val username: String = "Guest",
    val isThemeSheetVisible: Boolean = false,
    val selectedTheme: ThemeType = ThemeType.DARK,
    val isLanguageSheetVisible: Boolean = false,
    val selectedLanguage: LanguageType = LanguageType.ENGLISH,
    val settingType: SettingType = SettingType.THEME,
    val appVersion: String = "1.1.0v",
    val isGuest: Boolean = true,
    val isLogoutSheetVisible: Boolean = false,
)

enum class SettingType {
    THEME, LANGUAGE
}

enum class ThemeType(val value: String) {
    LIGHT("Light"),
    DARK("Dark")
}

enum class LanguageType(val value: String) {
    ENGLISH("English"),
    ARABIC("Arabic")
}