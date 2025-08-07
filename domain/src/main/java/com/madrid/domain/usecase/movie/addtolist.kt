package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.ListOperationStatus
import com.madrid.domain.entity.UserList
import com.madrid.domain.repository.MovieRepository

class CreateMovieListUseCase(private val movieRepository: MovieRepository) {
    suspend operator fun invoke(sessionId: String, name: String, description: String, language: String): ListOperationStatus {
        return movieRepository.createMovieList(sessionId, name, description, language)
    }

    class AddMovieToListUseCase(private val movieRepository: MovieRepository) {
        suspend operator fun invoke(sessionId: String, listId: Int, movieId: Int): ListOperationStatus {
            return movieRepository.addMovieToList(sessionId, listId, movieId)
        }
    }
}
