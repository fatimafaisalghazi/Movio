package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.domain.entity.RatedMovie
import com.madrid.domain.entity.RatedSeries

fun MovieResult.toRatedMovie(): RatedMovie {
    return RatedMovie(
        rate = this.rating ?: 0.0 ,
        movie = this.toMovie()
    )
}

fun SeriesResult.toRatedSeries():RatedSeries{
    return RatedSeries(
        rate = this.rating?: 0.0,
        series = this.toSeries()
    )
}

