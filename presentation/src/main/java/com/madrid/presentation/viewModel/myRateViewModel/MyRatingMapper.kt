package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase
import com.madrid.presentation.utils.RateFormatter
import com.madrid.presentation.viewModel.shared.MediaType

fun GetUserRatedMovieUseCase.RatedMovie.toRatedMediaUiState(): RatedMediaState {
    return RatedMediaState(
        mediaType = MediaType.MOVIE,
        mediaTitle = this.movie.title,
        rate = RateFormatter.formatRate(this.rate),
        mediaId = this.movie.id,
        date = RateFormatter.formatDate(this.date),
        imageUrl = this.movie.imageUrl
    )
}

fun GetUserRatedSeriesUseCase.RatedSeries.toRatedMediaUiState(): RatedMediaState {
    return RatedMediaState(
        mediaTitle = this.series.title,
        rate = RateFormatter.formatRate(this.rate),
        mediaType = MediaType.TV_SHOW,
        mediaId = this.series.id,
        date = RateFormatter.formatDate(this.date),
        imageUrl = this.series.imageUrl
    )
}