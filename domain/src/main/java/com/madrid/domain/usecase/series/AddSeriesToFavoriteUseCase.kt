package com.madrid.domain.usecase.series

import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AddSeriesToFavoriteUseCase @Inject constructor(
    val movieRepository: MovieRepository,
    val userRepository: UserRepository
) {
    suspend operator fun invoke(mediaId: Int) {
        val sessionId: String = userRepository.getSessionId().first()
        movieRepository.addMovieToFavorite(
            mediaId = mediaId,
            sessionId = sessionId
        )
    }
}