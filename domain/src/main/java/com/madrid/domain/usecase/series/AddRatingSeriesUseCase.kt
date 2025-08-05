package com.madrid.domain.usecase.series

import com.madrid.domain.repository.SeriesRepository

class AddRatingSeriesUseCase(
    private val seriesRepository: SeriesRepository
) {
    suspend operator fun invoke(movieId: Int, rate: Double) {
        return seriesRepository.addRatingSeries(movieId, rate)
    }
}