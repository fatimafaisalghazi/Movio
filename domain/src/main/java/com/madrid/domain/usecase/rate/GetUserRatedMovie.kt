package com.madrid.domain.usecase.rate


import com.madrid.domain.entity.RatedMovie
import com.madrid.domain.repository.MovieRateRepository


class GetUserRatedMovie(
    private val movieRateRepository: MovieRateRepository
){
    suspend operator fun invoke(accountId:Int):List<RatedMovie> =
        movieRateRepository.getUserMovieRate(accountId)
}
