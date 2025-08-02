package com.madrid.domain.entity


data class RatedMovie(
    val rate :Double,
    val movie: Movie
)

data class RatedSeries(
    val rate :Double,
    val series: Series
)
