package com.madrid.presentation.viewModel.libraryViewModel.viewAll

sealed interface ViewAllEffect {
    data object NavigateBack : ViewAllEffect
    data class NavigateToDetails(val mediaId: Int, val mediaType: String) : ViewAllEffect
}