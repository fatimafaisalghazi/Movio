package com.madrid.presentation.viewModel.uiStateMapper

import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.presentation.viewModel.shared.parser.formatRate
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState

fun Movie.toMovieUiState(): SearchScreenState.MovieUiState {
    return SearchScreenState.MovieUiState(
        id = id,
        title = title,
        imageUrl = imageUrl,
        rating = formatRate(rate),
        category = genre.toString()
    )
}

fun Series.toSeriesUiState(): SearchScreenState.SeriesUiState {
    return SearchScreenState.SeriesUiState(
        id = id.toString(),
        title = title,
        imageUrl = imageUrl,
        rating =formatRate(rate),
    )
}

fun Artist.toArtistUiState(): SearchScreenState.ArtistUiState {
    return SearchScreenState.ArtistUiState(
        id = id.toString(),
        name = name,
        imageUrl = imageUrl,
        role = role,
        country = country,
        description = overview
    )
}