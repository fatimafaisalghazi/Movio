package com.madrid.domain.usecase.rate

import com.madrid.domain.entity.RatedSeries
import com.madrid.domain.repository.SeriesRateRepository

class GetUserRatedSeries(
    private val seriesRateRepository: SeriesRateRepository
) {
    suspend operator fun invoke(accountId:Int):List<RatedSeries> =
    seriesRateRepository.getUserSeriesRate(accountId)
}