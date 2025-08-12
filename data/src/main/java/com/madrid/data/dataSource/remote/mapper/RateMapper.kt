package com.madrid.data.dataSource.remote.mapper

import com.madrid.data.dataSource.remote.dto.movie.MovieResult
import com.madrid.data.dataSource.remote.dto.series.SeriesResult
import com.madrid.domain.usecase.movie.GetUserRatedMovieUseCase
import com.madrid.domain.usecase.series.GetUserRatedSeriesUseCase
import java.text.SimpleDateFormat
import java.util.*

fun MovieResult.toRatedMovie(): GetUserRatedMovieUseCase.RatedMovie {
    return GetUserRatedMovieUseCase.RatedMovie(
        rate = this.rating ?: 0.0,
        movie = this.toMovie()
    )
}

fun SeriesResult.toRatedSeries(): GetUserRatedSeriesUseCase.RatedSeries {
    return GetUserRatedSeriesUseCase.RatedSeries(
        rate = this.rating ?: 0.0,
        series = this.toSeries()
    )
}


// Helper function to format review date
private fun formatReviewDate(dateString: String?): String {
    if (dateString.isNullOrEmpty()) return ""

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        date?.let { outputFormat.format(it) } ?: ""
    } catch (e: Exception) {
        // Fallback for different date formats
        try {
            val fallbackFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            val date = fallbackFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            dateString // Return original string if parsing fails
        }
    }
}