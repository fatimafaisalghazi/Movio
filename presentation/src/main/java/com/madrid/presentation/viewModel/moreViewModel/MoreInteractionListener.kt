package com.madrid.presentation.viewModel.moreViewModel

interface MoreInteractionListener {
    fun onThemeClick(isEnabled: Boolean)
    fun setDarkMode(isEnabled: Boolean)
    fun onLanguageClick()
    fun setLanguage(language: String)
    fun onLogoutClick()
    fun logout()
}