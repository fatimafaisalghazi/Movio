package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Series

interface SeeAllTVShowsStrategy {
    fun getTitle(): String
    suspend fun getAllTvShows(page: Int): List<Series>
    suspend fun getAllTvShowsCategories(): List<Genre>
    suspend fun getTvShowsBasedOnCategory(category: String): List<Series>
    fun showTvShowsCategory(): Boolean
}