package com.madrid.data.dataSource.remote.dto.rate

import com.google.gson.annotations.SerializedName
import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import kotlinx.serialization.Serializable

@Serializable
data class RatingMovieResponse(
    @SerializedName("series_id")
    val id: Int,
    @SerializedName("results")
    val ratedMovie: List<MovieResult>,
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)

@Serializable
data class RatingSeriesResponse(
    @SerializedName("series_id")
    val id: Int,
    @SerializedName("results")
    val ratedSeries: List<SeriesResult>,
    @SerializedName("total_pages")
    val totalPages: Int? = 0,
    @SerializedName("total_results")
    val totalResults: Int? = 0
)