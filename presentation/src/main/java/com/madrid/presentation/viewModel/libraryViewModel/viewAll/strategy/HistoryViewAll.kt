package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.movie.GetAllMoviesInHistoryUseCase
import com.madrid.presentation.viewModel.shared.MediaType
import javax.inject.Inject

class HistoryViewAll @Inject constructor(
    private val getHistoryUseCase: GetAllMoviesInHistoryUseCase,
//    private val deleteHistoryUseCase: DeleteMovieFromHistoryUseCase
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "History"
    }

    override suspend fun getAllItems(): List<Movie>  {
        return getHistoryUseCase()
    }

    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
//        deleteHistoryUseCase(mediaId, mediaType)
    }
}