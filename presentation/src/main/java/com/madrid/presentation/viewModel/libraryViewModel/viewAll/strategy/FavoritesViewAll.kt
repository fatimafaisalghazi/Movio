package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.domain.usecase.series.GetFavoriteSeriesUseCase
import com.madrid.presentation.viewModel.shared.MediaType
import com.madrid.presentation.viewModel.shared.MediaUiState
import com.madrid.presentation.viewModel.shared.toMediaUiState
import dagger.assisted.Assisted
import javax.inject.Inject

class FavoritesViewAll @Inject constructor(
    private val getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase,
    private val getFavoriteSeriesUseCase: GetFavoriteSeriesUseCase,
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase
//    private val deleteFavoriteUseCase: AddMovieToFavoriteUseCase
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "Favorites"
    }

    override suspend fun getAllItems(): List<MediaUiState> {
        return (getFavoriteMoviesUseCase().map { it.toMediaUiState() } +
                getFavoriteSeriesUseCase().map { it.toMediaUiState() })
    }


    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
        // TODO: Implement delete functionality
    }
}