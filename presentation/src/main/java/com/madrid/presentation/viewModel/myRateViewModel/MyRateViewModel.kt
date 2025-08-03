package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.rate.GetUserRatedMovie
import com.madrid.domain.usecase.rate.GetUserRatedSeries
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.MediaType


class MyRateViewModel(
    private val getUserRatedSeries: GetUserRatedSeries,
    private val getUserRatedMovie: GetUserRatedMovie
) : BaseViewModel<MyRateUiState, MyRatingEffect>(MyRateUiState()),
    MyRatingInteractionListener {
    override fun onMediaClick(mediaId: Int, mediaType: MediaType) {
        emitNewEffect(effect = MyRatingEffect.NavigateToMediaDetails(mediaId, mediaType))
    }

    override fun onBackClick() {
        emitNewEffect(effect = MyRatingEffect.NavigateBack)
    }

    fun loadRatedSeries(accountId: Int) {
        tryToExecute(
            function = { getUserRatedSeries.invoke(accountId) },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        ratedSeries = result.map {
                            RatedSeriesState(
                                imageUrL = it.series.imageUrl,
                                mediaTitle = it.series.title,
                                rate = it.rate.toString(),
                            )
                        },
                        isLoading = false
                    )
                }
            },
            onError = {
                //TODO
            },
        )
    }

    fun loadRatedMovie(accountId: Int) {
        tryToExecute(
            function = { getUserRatedMovie.invoke(accountId) },
            onSuccess = { result ->
                updateState {
                    it.copy(
                        ratedMovie = result.map {
                            RatedMovieState(
                                imageUrL = it.movie.imageUrl,
                                mediaTitle = it.movie.title,
                                rate = it.rate.toString()
                            )
                        },
                        isLoading = false
                    )
                }
            },
            onError = {
                //TODO
            },
        )
    }

}