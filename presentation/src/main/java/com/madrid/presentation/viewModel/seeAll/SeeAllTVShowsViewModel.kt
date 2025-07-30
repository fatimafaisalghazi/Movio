package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Series
import com.madrid.presentation.viewModel.base.BaseViewModel

class SeeAllTVShowsViewModel(
    private val strategy: SeeAllTVShowsStrategy,
) : BaseViewModel<SeeAllTVShowsUiState, SeeAllEffect>(SeeAllTVShowsUiState()),
    SeeAllTVShowsInteractionListener {

    init {
        loadTitle()
        loadGenres()
        loadAllSeries()
    }

    private fun loadTitle() {
        updateState { it.copy(title = strategy.getTitle()) }
    }

    private fun loadGenres() {
        tryToExecute(
            function = { strategy.getAllTvShowsCategories() },
            onSuccess = { genres ->
                updateState { it.copy(genre = genres.map { genre -> genre.name }) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    private fun loadAllSeries() {
        tryToExecute(
            function = { strategy.getAllTvShows(1) },
            onSuccess = { allSeries ->
                updateState { it.copy(filteredSeries = allSeries.map { series -> series.toUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }


    override fun onGenreSelect(genre: String) {
        tryToExecute(
            function = { strategy.getTvShowsBasedOnCategory(genre) },
            onSuccess = { allSeries ->
                updateState { it.copy(filteredSeries = allSeries.map { series -> series.toUiState() }) }
            },
            onError = { /* Handle if needed */ }
        )
    }

    override fun onSeriesClick(seriesId: Int) {
        emitNewEffect(SeeAllEffect.NavigateToSeriesDetails(seriesId))
    }

    override fun onBackClick() {
        emitNewEffect(effect = SeeAllEffect.OnNavigateBack)
    }

    override fun onClickAllChip() {
        loadAllSeries()
    }
}

fun Series.toUiState(): SeriesUiState {
    return SeriesUiState(
        id = this.id.toString(),
        imageUrl = imageUrl,
        rate = this.rate.toString(),
        name = this.title,
        genre = this.genre,
    )
}