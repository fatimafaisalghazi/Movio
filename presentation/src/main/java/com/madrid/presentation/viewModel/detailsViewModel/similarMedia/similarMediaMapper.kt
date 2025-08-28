package com.madrid.presentation.viewModel.detailsViewModel.similarMedia

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series

fun List<Movie>.toMovieUiState(): List<MediaUiState> {
    return this.map { movie ->
        MediaUiState(
            mediaId = movie.id,
            isMovie = true,
            imageUrl = movie.imageUrl,
            mediaName = movie.title,
            rate = movie.rate?.let {
                if (it > 0.0) String.format("%.1f", it) else ""
            } ?: ""
        )
    }
}

fun List<Series>.toSeriesUiState(): List<MediaUiState> {
    return this.map { series ->
        MediaUiState(
            mediaId = series.id,
            isMovie = false,
            imageUrl = series.imageUrl,
            mediaName = series.title,
            rate = series.rate?.let {
                if (it > 0.0) String.format("%.1f", it) else ""
            } ?: ""
        )
    }
}