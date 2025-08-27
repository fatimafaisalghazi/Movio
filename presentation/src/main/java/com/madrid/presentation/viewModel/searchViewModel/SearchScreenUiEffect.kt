package com.madrid.presentation.viewModel.searchViewModel

sealed interface SearchScreenUiEffect{
    data class NavigateToMovieDetails(val movieId: Int): SearchScreenUiEffect
    data class NavigateToSeriesDetails(val seriesId: Int, val seasonNumber: Int): SearchScreenUiEffect
    data class NavigateToActorDetails(val actorId: Int): SearchScreenUiEffect
    data object NavigateToSeeAllScreen: SearchScreenUiEffect
    data object NavigateBack: SearchScreenUiEffect
}