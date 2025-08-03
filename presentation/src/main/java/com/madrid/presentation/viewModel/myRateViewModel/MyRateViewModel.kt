package com.madrid.presentation.viewModel.myRateViewModel

import com.madrid.domain.usecase.movie.GetUserRatedMovie
import com.madrid.domain.usecase.series.GetUserRatedSeries
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.shared.MediaType


class MyRateViewModel(
    private val getUserRatedSeries: GetUserRatedSeries,
    private val getUserRatedMovie: GetUserRatedMovie
) : BaseViewModel<MyRateUiState, MyRatingEffect>(MyRateUiState()),
    MyRatingInteractionListener {

//    init {
//        if () {
//            loadRatedSeries()
//        } else {loadRatedMovie()
//        }
//    }

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
                        ratedMedia = result.map { it.toRatedSeriesUiState() },
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
                        ratedMedia = result.map { it.toRatedMovieUiState() },
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