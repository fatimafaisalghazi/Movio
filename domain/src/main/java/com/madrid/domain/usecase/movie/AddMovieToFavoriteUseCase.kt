package com.madrid.domain.usecase.movie

import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class AddMovieToFavoriteUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    val userRepository: UserRepository
) {
     operator fun invoke(mediaId: Int) =
        runBlocking {
            val sessionId = userRepository.getSessionId()
            movieRepository.addMovieToFavorite(
                mediaId = mediaId,
                sessionId = sessionId.toString()
            )
        }
}