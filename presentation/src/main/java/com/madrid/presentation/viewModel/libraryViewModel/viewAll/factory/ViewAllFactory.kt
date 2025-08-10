package com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory

import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.domain.usecase.series.GetAllSeriesInHistoryUseCase
import com.madrid.domain.usecase.series.GetFavoriteSeriesUseCase
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.factory.ViewAllType
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.FavoritesViewAll
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.HistoryViewAll
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.ViewAllStrategy
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy.WatchlistViewAll
import javax.inject.Inject

class ViewAllFactory @Inject constructor(
    private val watchlistViewAll: WatchlistViewAll,
    private val favoritesViewAll: FavoritesViewAll,
    private val historyViewAll: HistoryViewAll
) {
    fun create(type: ViewAllType): ViewAllStrategy {
        return when (type) {
            ViewAllType.WATCHLIST -> watchlistViewAll
            ViewAllType.FAVORITES -> favoritesViewAll
            ViewAllType.HISTORY -> historyViewAll
        }
    }
}