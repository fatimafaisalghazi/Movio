package com.madrid.presentation.viewModel.moreViewModel

interface MoreInteractionListener {
    fun onLoginBtnClick()
    fun onMyRatingsBtnClick()
    fun onClickTheme()
    fun onConfirmTheme(themeType: ThemeType)
    fun onClickLanguage()
    fun onConfirmLanguage(languageType: LanguageType)
    fun onDismissBottomSheet()
    fun onLogoutBtnClick()
    fun logout()
}