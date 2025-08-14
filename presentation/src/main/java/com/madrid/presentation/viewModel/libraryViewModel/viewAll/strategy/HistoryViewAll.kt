package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.domain.usecase.series.GetAllSeriesInHistoryUseCase
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import com.madrid.presentation.viewModel.shared.toMediaUiState
import javax.inject.Inject

class HistoryViewAll @Inject constructor(
    private val getMoviesHistoryUseCase: GetAllMoviesInHistoryUseCase,
    private val getSeriesHistoryUseCase: GetAllSeriesInHistoryUseCase
//    private val deleteHistoryUseCase: DeleteMovieFromHistoryUseCase
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "History"
    }

    override fun getEmptyListMessage(): Int {
        return R.string.Start_adding_the_movies_and_shows_you_love
    }

    override suspend fun getAllItems(): List<MediaUiState> {
        return (getMoviesHistoryUseCase()
            .map { it.toMediaUiState() }
                + getSeriesHistoryUseCase()
            .map { it.toMediaUiState() })
    }

    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
//        deleteHistoryUseCase(mediaId, mediaType)
    }

    override suspend fun onUndoDelete(
        mediaId: String,
        mediaType: MediaType
    ) {
        TODO("Not yet implemented")
    }
}