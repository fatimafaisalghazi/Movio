package com.madrid.domain.repository

import com.madrid.domain.entity.Rate

interface MovieRateRepository {
    suspend fun getUserMovieRate(accountId:Int): List<Rate>
}

interface SeriesRateRepository{
    suspend fun getUserSeriesRate(accountId:Int):List<Rate>
}