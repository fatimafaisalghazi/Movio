package com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails


sealed interface SeriesDetailsEffect {
    data object NavigateBack : SeriesDetailsEffect
    data object RetryLoadData :SeriesDetailsEffect
    data class NavigateToSeriesDetails(val seriesId: Int) : SeriesDetailsEffect
}