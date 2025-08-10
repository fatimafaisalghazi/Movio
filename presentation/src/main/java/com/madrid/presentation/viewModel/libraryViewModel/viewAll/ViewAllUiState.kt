package com.madrid.presentation.viewModel.libraryViewModel.viewAll

import com.madrid.presentation.viewModel.shared.MediaUiState

data class ViewAllUiState(
    val title: String = "",
    val items: List<MediaUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
)
