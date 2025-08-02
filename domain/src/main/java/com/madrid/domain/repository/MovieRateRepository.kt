package com.madrid.domain.repository

import com.madrid.domain.entity.RatedMovie
import com.madrid.domain.entity.RatedSeries

interface MovieRateRepository {
    suspend fun getUserMovieRate(accountId:Int):List<RatedMovie>
}

interface SeriesRateRepository{
    suspend fun getUserSeriesRate(accountId:Int): List<RatedSeries>
}