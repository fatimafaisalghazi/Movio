package com.madrid.presentation.viewModel.detailsViewModel.similarMedia

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimilarMediaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarSeriesUseCase: GetSimilarSeriesUseCase,
) : BaseViewModel<SimilarMediaUiState, Nothing>(SimilarMediaUiState()) {

    private val args = savedStateHandle.toRoute<Destinations.SimilarMediaScreen>()

    init {
        if (args.isMovie) loadSimilarMedia()
        else loadSimilarSeries()
    }

    private fun loadSimilarMedia() {
        tryToExecute(
            function = { getSimilarMoviesUseCase(movieId = args.mediaId) },
            onSuccess = { allMovies ->
                updateState {
                    it.copy(
                        headerName = "Similar Movies",
                        medias = allMovies.toMovieUiState(),
                        isMovie = true
                    )
                }
            },
            onError = { e ->

            },
        )
    }

    private fun loadSimilarSeries() {
        tryToExecute(
            function = { getSimilarSeriesUseCase(seriesId = args.mediaId) },
            onSuccess = { allSeries ->
                updateState {
                    it.copy(
                        headerName = "Similar Series",
                        medias = allSeries.toSeriesUiState(),
                        isMovie = false
                    )
                }
            },
            onError = { e ->
                Log.d("TAG lol", "loadCastData: ${e.message}")
            },
        )
    }

}


