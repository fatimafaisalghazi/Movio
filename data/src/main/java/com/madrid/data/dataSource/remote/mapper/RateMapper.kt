package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.rate.RatingMovieResponse
import com.madrid.data.dataSource.remote.dto.rate.RatingSeriesResponse
import com.madrid.domain.entity.Rate

fun RatingMovieResponse.toMovieRated(): List<Rate> {
    return ratedMovie.mapNotNull { movie ->
        movie.id?.let {
            Rate(
                id = it,
                rate = movie.rating
            )
        }
    }
}

fun RatingSeriesResponse.toSeriesRated():List<Rate>{
    return ratedSeries.mapNotNull { series ->
        series.id?.let {
            Rate(id = it,
                rate = series.rating
            )
        }
    }
}

