package com.madrid.data.repositories.remote.rateRepositoryImpl

import android.util.Log
import com.madrid.data.dataSource.remote.mapper.toRatedMovie
import com.madrid.data.repositories.local.LocalDataSource
import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.entity.RatedMovie
import com.madrid.domain.repository.MovieRateRepository

class MovieRateRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : MovieRateRepository {

    override suspend fun getUserMovieRate(accountId: Int): List<RatedMovie> {
        val result = remoteDataSource.getUserRatingForMovie(accountId)
        Log.d("result", "getUserMovieRate: $result")
        return result.ratedMovie.map { it.toRatedMovie() }
    }
}