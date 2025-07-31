package com.madrid.domain.usecase.series

import com.madrid.domain.entity.Series
import com.madrid.domain.entity.SortType
import com.madrid.domain.repository.SeriesRepository

class GetSeriesByGenreIdUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(page: Int, genreId: Int?, sortBy: SortType): List<Series> {
        return seriesRepository.getSeriesByGenreId(page, genreId, sortBy)
    }
}