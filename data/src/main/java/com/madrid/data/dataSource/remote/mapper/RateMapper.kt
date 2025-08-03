package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.domain.usecase.movie.GetUserRatedMovie
import com.madrid.domain.usecase.series.GetUserRatedSeries

fun MovieResult.toRatedMovie(): GetUserRatedMovie.RatedMovie {
    return GetUserRatedMovie.RatedMovie(
        rate = this.rating ?: 0.0,
        movie = this.toMovie()
    )
}

fun SeriesResult.toRatedSeries(): GetUserRatedSeries.RatedSeries {
    return GetUserRatedSeries.RatedSeries(
        rate = this.rating ?: 0.0,
        series = this.toSeries()
    )
}

