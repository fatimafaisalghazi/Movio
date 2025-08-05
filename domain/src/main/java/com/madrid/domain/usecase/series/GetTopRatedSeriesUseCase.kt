package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository

class GetTopRatedSeriesUseCase(
    val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(page: Int) =
        seriesRepository.getTopRatedSeries(page = page)
}