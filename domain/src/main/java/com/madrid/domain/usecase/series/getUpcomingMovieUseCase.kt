package com.madrid.domain.usecase.homeUseCase

import com.madrid.domain.repository.MovieRepository

class getUpcomingMovieUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke() = movieRepository.getUpcomingMovie()
}