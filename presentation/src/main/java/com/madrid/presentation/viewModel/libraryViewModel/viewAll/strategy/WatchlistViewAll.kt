package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.usecase.watchList.GetWatchListsUseCase
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import javax.inject.Inject

class WatchlistViewAll @Inject constructor(
    private val getWatchListUseCase: GetWatchListsUseCase ,
//    private val deleteWatchListUseCase: DeleteWatchListUseCase
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "Watchlist"
    }

    override suspend fun getAllItems(): List<MediaUiState> {
        return emptyList()  // getWatchListUseCase().map { it.toMediaUiState() }
    }

    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
//        deleteWatchListUseCase(mediaId, mediaType)
    }
}