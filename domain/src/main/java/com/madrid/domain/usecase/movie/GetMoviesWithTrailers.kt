package com.madrid.domain.usecase.movie

import com.madrid.domain.entity.Movie
import com.madrid.domain.entity.Trailer
import com.madrid.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesWithTrailers @Inject constructor(
    val movieTrailersUseCase: GetMovieTrailersUseCase,
    val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movies: List<Movie>): List<Movie> {
        return movies.map { movie ->
            val trailer = movieTrailersUseCase(movie.id).first()
            movie.copy(
                trailer = Trailer(
                    key = trailer.key,
                    id = trailer.id
                )
            )
        }
    }
}