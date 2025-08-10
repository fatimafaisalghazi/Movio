package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.authentication.GetCurrentUserDetailsUseCase
import com.madrid.domain.usecase.movie.GetFavoriteMoviesUseCase
import com.madrid.presentation.viewModel.shared.MediaType
import dagger.assisted.Assisted
import javax.inject.Inject

class FavoritesViewAll @Inject constructor(
    private val getFavoriteUseCase: GetFavoriteMoviesUseCase,
    private val getCurrentUserDetailsUseCase: GetCurrentUserDetailsUseCase
//    private val deleteFavoriteUseCase: AddMovieToFavoriteUseCase
) : ViewAllStrategy {

    override fun getTitle(): String {
        return "Favorites"
    }

    override suspend fun getAllItems(): List<Movie> {
        return getFavoriteUseCase()
    }


    override suspend fun deleteItem(mediaId: String, mediaType: MediaType) {
        // TODO: Implement delete functionality
    }
}