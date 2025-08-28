package com.madrid.presentation.viewModel.detailsViewModel.similarMedia

sealed interface SimilarMediaEffect {
    data class NavigateToDetails(val id: Int): SimilarMediaEffect
    data object NavigateBacK: SimilarMediaEffect
}