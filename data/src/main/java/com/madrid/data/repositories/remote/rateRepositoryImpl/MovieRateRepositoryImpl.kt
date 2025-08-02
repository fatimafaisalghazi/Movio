package com.madrid.data.repositories.remote.rateRepositoryImpl

import android.util.Log
import com.madrid.data.dataSource.remote.mapper.toMovieRated
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.Rate
import com.madrid.domain.repository.MovieRateRepository

class MovieRateRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MovieRateRepository {

    override suspend fun getUserMovieRate(accountId: Int): List<Rate> {
        val result = remoteDataSource.getUserRatingForMovie(accountId).toMovieRated()
        Log.d("result", "getUserMovieRate: $result")
        return result
    }
}