package com.madrid.data.repositories.remote.rateRepositoryImpl

import android.util.Log
import com.madrid.data.dataSource.remote.mapper.toRatedSeries
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.RatedSeries
import com.madrid.domain.repository.SeriesRateRepository

class SeriesRateRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : SeriesRateRepository {
    override suspend fun getUserSeriesRate(accountId: Int): List<RatedSeries> {
        val result =
            remoteDataSource.getUserRatingForSeries(accountId)
        Log.d("result", "getUserSeriesRate: $result")
        return result.ratedSeries.map { it.toRatedSeries() }
    }
}