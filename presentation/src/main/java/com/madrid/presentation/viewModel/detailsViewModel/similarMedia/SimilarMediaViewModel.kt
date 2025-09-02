package com.madrid.presentation.viewModel.detailsViewModel.similarMedia

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.base.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SimilarMediaViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getSimilarMoviesUseCase: GetSimilarMoviesUseCase,
    private val getSimilarSeriesUseCase: GetSimilarSeriesUseCase,
) : BaseViewModel<SimilarMediaUiState, SimilarMediaEffect>(initialState = SimilarMediaUiState())
    , SimilarMediaInteractionListener
{

    private val args = savedStateHandle.toRoute<Destinations.SimilarMediaScreen>()

    init {
        if (args.isMovie) loadSimilarMovie() else loadSimilarSeries()
    }

    private fun loadSimilarMovie() {
        ::initFetchData.invoke()
        tryToExecute(
            function = { getSimilarMoviesUseCase.invoke(movieId = args.mediaId) },
            onSuccess = { allMovies -> ::onSuccessLoadSimilarMovie.invoke(allMovies) },
            onError = ::onError
        )
    }

    private fun loadSimilarSeries() {
        ::initFetchData.invoke()
        tryToExecute(
            function = { getSimilarSeriesUseCase.invoke(seriesId = args.mediaId) },
            onSuccess = { allSeries -> ::onSuccessLoadSimilarSeries.invoke(allSeries)},
            onError = ::onError
        )
    }

    private fun initFetchData() {
        updateState { it.copy(showLoadingScreen = true) }
    }

    private fun onError(error : ErrorState) {
        updateState {
            it.copy(isError = true, showLoadingScreen = false)
        }
    }

    private fun onSuccessLoadSimilarMovie(allMovies: List<Movie>) {
        updateState {
            it.copy(
                headerName = "Similar Movies",
                medias = allMovies.toMovieUiState(),
                isMovie = true,
                showLoadingScreen = false,
                isError = false
            )
        }
    }

    private fun onSuccessLoadSimilarSeries(allSeries: List<Series>) {
        updateState {
            it.copy(
                headerName = "Similar Series",
                medias = allSeries.toSeriesUiState(),
                isMovie = false,
                showLoadingScreen = false,
                isError = false
            )
        }
    }

    override fun onMediaCardClick(id: Int) {
        emitNewEffect(effect=SimilarMediaEffect.NavigateToDetails(id))
    }

    override fun onBackClick() {
        emitNewEffect(effect= SimilarMediaEffect.NavigateBacK)
    }

    override fun onRetryButtonClick() {
        if (args.isMovie) loadSimilarMovie() else loadSimilarSeries()
    }
}