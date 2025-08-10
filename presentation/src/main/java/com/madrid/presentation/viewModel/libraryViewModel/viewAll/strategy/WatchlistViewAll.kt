package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.WatchList
import com.madrid.domain.usecase.watchList.GetWatchListItemsUseCase
import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.shared.MediaType
import javax.inject.Inject

class WatchlistViewAll @Inject constructor(
    private val getWatchListUseCase: GetWatchListItemsUseCase ,
//    private val deleteWatchListUseCase: DeleteWatchListUseCase
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "Watchlist"
    }

    override suspend fun getAllItems(): List<Movie> {
//        return getWatchListUseCase(id).movies
        return emptyList()
    }

    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
//        deleteWatchListUseCase(mediaId, mediaType)
    }
}