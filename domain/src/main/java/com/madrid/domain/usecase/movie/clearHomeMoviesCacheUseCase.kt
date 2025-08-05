package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository

class ClearHomeMoviesCacheUseCase(private val moviesRepository: MovieRepository) {
    suspend operator fun invoke() {
        moviesRepository.clearHomeMoviesCache()
    }
}