package com.madrid.presentation.viewModel.libraryViewModel.viewAll.strategy

import com.madrid.domain.entity.Movie
import com.madrid.presentation.viewModel.shared.MediaType

interface ViewAllStrategy {
    fun getTitle(): String
    suspend fun getAllItems(): List<Movie>
    suspend fun deleteItem(mediaId: String, mediaType: MediaType)
}