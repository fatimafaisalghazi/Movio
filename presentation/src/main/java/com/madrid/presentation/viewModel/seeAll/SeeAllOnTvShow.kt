package com.madrid.presentation.viewModel.seeAll

import com.madrid.domain.entity.Genre
import com.madrid.domain.entity.Series
import com.madrid.domain.usecase.series.GetOnAirSeriesUseCase

class SeeAllOnTvShow(
    private val getOnAirSeriesUseCase: GetOnAirSeriesUseCase
) : SeeAllTVShowsStrategy {
    override fun getTitle(): String {
        return "On TV"
    }

    override suspend fun getAllTvShows(page: Int): List<Series> {
        return getOnAirSeriesUseCase(1)
    }

    override suspend fun getAllTvShowsCategories(): List<Genre> {
        TODO("Not yet implemented")
    }

    override suspend fun getTvShowsBasedOnCategory(category: String): List<Series> {
        TODO("Not yet implemented")
    }
    override fun showTvShowsCategory(): Boolean {
        return false
    }

}