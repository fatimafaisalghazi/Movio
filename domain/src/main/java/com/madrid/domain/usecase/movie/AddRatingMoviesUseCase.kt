package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository

class AddRatingMoviesUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int, rate: Double) {
        return movieRepository.addRatingMovie(movieId, rate)
    }
}