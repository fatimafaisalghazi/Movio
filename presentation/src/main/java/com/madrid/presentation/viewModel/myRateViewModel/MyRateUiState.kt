package com.madrid.presentation.viewModel.myRateViewModel

data class MyRateUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val ratedMovie: List<RatedMovieState> = emptyList(),
    val ratedSeries: List<RatedSeriesState> = emptyList()
)

data class RatedMovieState(
    val imageUrL: String = "",
    val mediaTitle: String = "",
    val rate: String = ""
)
data class RatedSeriesState (
    val imageUrL: String = "",
    val mediaTitle: String = "",
    val rate: String = ""
)