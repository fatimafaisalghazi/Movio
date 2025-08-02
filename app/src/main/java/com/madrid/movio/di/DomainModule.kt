package com.madrid.movio.di

import com.madrid.data.repositories.UserRepositoryImpl
import com.madrid.domain.repository.ArtistRepository
import com.madrid.domain.repository.MovieRepository
import com.madrid.domain.repository.SearchRepository
import com.madrid.domain.repository.SeriesRepository
import com.madrid.domain.repository.UserRepository
import com.madrid.domain.usecase.artist.GetArtistDetailsUseCase
import com.madrid.domain.usecase.artist.GetArtistMoviesUseCase
import com.madrid.domain.usecase.authentication.LoginUseCase
import com.madrid.domain.usecase.movie.FilterMoviesByCategoryUseCase
import com.madrid.domain.usecase.movie.GetMovieDetailsUseCase
import com.madrid.domain.usecase.movie.GetMovieGenresUseCase
import com.madrid.domain.usecase.movie.GetMovieReviewsUseCase
import com.madrid.domain.usecase.movie.GetMovieTopCastUseCase
import com.madrid.domain.usecase.movie.GetMovieTrailersUseCase
import com.madrid.domain.usecase.movie.GetMoviesByGenreIdUseCase
import com.madrid.domain.usecase.movie.GetMoviesByGenresUseCase
import com.madrid.domain.usecase.movie.GetNowPlayingMovieUseCase
import com.madrid.domain.usecase.movie.GetSimilarMoviesUseCase
import com.madrid.domain.usecase.movie.GetTopRatedMoviesUseCase
import com.madrid.domain.usecase.movie.GetTrendingMoviesUseCase
import com.madrid.domain.usecase.movie.GetUpcomingMovieUseCase
import com.madrid.domain.usecase.search.AddRecentSearchUseCase
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetArtistsByQueryUseCase
import com.madrid.domain.usecase.search.GetExploreMoreMovieUseCase
import com.madrid.domain.usecase.search.GetMoviesByQueryUseCase
import com.madrid.domain.usecase.search.GetPopularMoviesUseCase
import com.madrid.domain.usecase.search.GetRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.search.GetSeriesByQueryUseCase
import com.madrid.domain.usecase.search.RemoveRecentSearchUseCase
import com.madrid.domain.usecase.series.FilterSeriesByCategoryUseCase
import com.madrid.domain.usecase.series.GetAiringTodaySeriesUseCase
import com.madrid.domain.usecase.series.GetEpisodesForSeasonUseCase
import com.madrid.domain.usecase.series.GetOnAirSeriesUseCase
import com.madrid.domain.usecase.series.GetRecommendedSeriesUseCase
import com.madrid.domain.usecase.series.GetSeriesByGenreIdUseCase
import com.madrid.domain.usecase.series.GetSeriesByGenresUseCase
import com.madrid.domain.usecase.series.GetSeriesDetailsUseCase
import com.madrid.domain.usecase.series.GetSeriesGenresUseCase
import com.madrid.domain.usecase.series.GetSeriesReviewsUseCase
import com.madrid.domain.usecase.series.GetSeriesTopCastUseCase
import com.madrid.domain.usecase.series.GetSeriesTrailersUseCase
import com.madrid.domain.usecase.series.GetSimilarSeriesUseCase
import com.madrid.domain.usecase.series.GetTopRatedSeriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetArtistMoviesUseCase(
        artistRepository: ArtistRepository
    ): GetArtistMoviesUseCase {
        return GetArtistMoviesUseCase(artistRepository)
    }
    @Provides
    fun provideGetArtistDetailsUseCase(
        artistRepository: ArtistRepository
    ): GetArtistDetailsUseCase {
        return GetArtistDetailsUseCase(artistRepository)
    }
    @Provides
    fun provideAddRecentSearchUseCase(
        searchRepository: SearchRepository
    ): AddRecentSearchUseCase {
        return AddRecentSearchUseCase(searchRepository)
    }
    @Provides
    fun provideClearAllRecentSearchesUseCase(
        searchRepository: SearchRepository
    ): ClearAllRecentSearchesUseCase {
        return ClearAllRecentSearchesUseCase(searchRepository)
    }
    @Provides
    fun provideGetArtistsByQueryUseCase(
        searchRepository: SearchRepository
    ): GetArtistsByQueryUseCase {
        return GetArtistsByQueryUseCase(searchRepository)
    }
    @Provides
    fun provideGetExploreMoreMovieUseCase(
        movieRepository: MovieRepository
    ): GetExploreMoreMovieUseCase {
        return GetExploreMoreMovieUseCase(movieRepository)
    }
    @Provides
    fun provideGetMoviesByQueryUseCase(
        searchRepository: SearchRepository,
        movieRepository: MovieRepository
    ): GetMoviesByQueryUseCase {
        return GetMoviesByQueryUseCase(movieRepository , searchRepository)
    }
    @Provides
    fun provideGetPopularMoviesUseCase(
        movieRepository: MovieRepository
    ): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepository)
    }
    @Provides
    fun provideGetRecentSearchesUseCase(
        searchRepository: SearchRepository
    ): GetRecentSearchesUseCase {
        return GetRecentSearchesUseCase(searchRepository)
    }
    @Provides
    fun provideGetRecommendedMovieUseCase(
        movieRepository: MovieRepository
    ): GetRecommendedMovieUseCase {
        return GetRecommendedMovieUseCase(movieRepository)
    }
    @Provides
    fun provideGetSeriesByQueryUseCase(
        searchRepository: SearchRepository,
        seriesRepository: SeriesRepository
    ): GetSeriesByQueryUseCase {
        return GetSeriesByQueryUseCase(seriesRepository , searchRepository)
    }
    @Provides
    fun provideRemoveRecentSearchUseCase(
        searchRepository: SearchRepository
        ): RemoveRecentSearchUseCase {
        return RemoveRecentSearchUseCase(searchRepository)
        }
    @Provides
    fun provideGetMovieDetailsUseCase(
        movieRepository: MovieRepository
    ): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(movieRepository)
    }
    @Provides
    fun provideGetMovieReviewsUseCase(
        movieRepository: MovieRepository
        ): GetMovieReviewsUseCase {
        return GetMovieReviewsUseCase(movieRepository)
    }
    @Provides
    fun provideGetMovieTopCastUseCase(
        movieRepository: MovieRepository
    ): GetMovieTopCastUseCase {
        return GetMovieTopCastUseCase(movieRepository)
    }
    @Provides
    fun provideGetMovieTrailersUseCase(
        movieRepository: MovieRepository
        ): GetMovieTrailersUseCase {
        return GetMovieTrailersUseCase(movieRepository)
    }
    @Provides
    fun provideGetSimilarMoviesUseCase(
        movieRepository: MovieRepository
        ): GetSimilarMoviesUseCase {
        return GetSimilarMoviesUseCase(movieRepository)
    }
    @Provides
    fun provideGetMovieGenresUseCase(
        movieRepository: MovieRepository
        ): GetMovieGenresUseCase {
        return GetMovieGenresUseCase(movieRepository)

        }
    @Provides
    fun provideGetTopRatedMoviesUseCase(
        movieRepository: MovieRepository
        ): GetTopRatedMoviesUseCase {
        return GetTopRatedMoviesUseCase(movieRepository)
    }
    @Provides
    fun provideGetUpcomingMovieUseCase(
        movieRepository: MovieRepository
        ): GetUpcomingMovieUseCase {
        return GetUpcomingMovieUseCase(movieRepository)
    }
    @Provides
    fun provideGetNowPlayingMovieUseCase(
        movieRepository: MovieRepository
        ): GetNowPlayingMovieUseCase {
        return GetNowPlayingMovieUseCase(movieRepository)
    }
    @Provides
    fun provideGetTrendingMoviesUseCase(
        movieRepository: MovieRepository
        ): GetTrendingMoviesUseCase {
        return GetTrendingMoviesUseCase(movieRepository)

        }
    @Provides
    fun provideFilterMoviesByCategoryUseCase(
        movieRepository: MovieRepository
        ): FilterMoviesByCategoryUseCase {
        return FilterMoviesByCategoryUseCase()
        }
    @Provides
    fun provideGetSeriesByGenresUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesByGenresUseCase {
        return GetSeriesByGenresUseCase(seriesRepository)
    }
    @Provides
    fun provideGetEpisodesForSeasonUseCase(
        seriesRepository: SeriesRepository
        ): GetEpisodesForSeasonUseCase {
        return GetEpisodesForSeasonUseCase(seriesRepository)}
    @Provides
    fun provideGetSeriesDetailsUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesDetailsUseCase {
        return GetSeriesDetailsUseCase(seriesRepository)}
    @Provides
    fun provideGetSeriesReviewsUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesReviewsUseCase {
        return GetSeriesReviewsUseCase(seriesRepository)}
    @Provides
    fun provideGetSeriesTopCastUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesTopCastUseCase {
        return GetSeriesTopCastUseCase(seriesRepository)}
    @Provides
    fun provideGetSeriesTrailersUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesTrailersUseCase {
        return GetSeriesTrailersUseCase(seriesRepository)}
    @Provides
    fun provideGetSimilarSeriesUseCase(
        seriesRepository: SeriesRepository
        ): GetSimilarSeriesUseCase {
        return GetSimilarSeriesUseCase(seriesRepository)}
    @Provides
    fun provideFilterSeriesByCategoryUseCase(
        seriesRepository: SeriesRepository
        ): FilterSeriesByCategoryUseCase {
        return FilterSeriesByCategoryUseCase()}
    @Provides
    fun provideGetAiringTodaySeriesUseCase(
        seriesRepository: SeriesRepository
        ): GetAiringTodaySeriesUseCase {
        return GetAiringTodaySeriesUseCase(seriesRepository)}

    @Provides
    fun provideGetOnAirSeriesUseCase(
        seriesRepository: SeriesRepository
        ): GetOnAirSeriesUseCase {
        return GetOnAirSeriesUseCase(seriesRepository)}
    @Provides
    fun provideGetRecommendedSeriesUseCase(
        seriesRepository: SeriesRepository
        ): GetRecommendedSeriesUseCase {
        return GetRecommendedSeriesUseCase(seriesRepository)}
    @Provides
    fun provideGetSeriesGenresUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesGenresUseCase {
        return GetSeriesGenresUseCase(seriesRepository)}
    @Provides
    fun provideGetTopRatedSeriesUseCase(
        seriesRepository: SeriesRepository
        ): GetTopRatedSeriesUseCase {
        return GetTopRatedSeriesUseCase(seriesRepository)}
    @Provides
    fun provideGetSeriesByGenreIdUseCase(
        seriesRepository: SeriesRepository
        ): GetSeriesByGenreIdUseCase {
        return GetSeriesByGenreIdUseCase(seriesRepository)}


    @Provides
    fun provideLoginUseCase(
//        userRepository: UserRepository
    ): LoginUseCase {
        return LoginUseCase(
//            userRepository
        )
    }
    @Provides
    fun provideGetMoviesByGenresUseCase(
        movieRepository: MovieRepository
    ): GetMoviesByGenresUseCase {
        return GetMoviesByGenresUseCase(movieRepository)
        }
    @Provides
    fun provideGetMoviesByGenreIdUseCase(
        movieRepository: MovieRepository
    ): GetMoviesByGenreIdUseCase {
        return GetMoviesByGenreIdUseCase(movieRepository)
    }

    @Provides
    fun provideDispatchersIo(
    ): kotlinx.coroutines.CoroutineDispatcher {
        return Dispatchers.IO
    }

}
//val domainModule = module {
//    // artist
//    singleOf(::GetArtistMoviesUseCase)
//    singleOf(::GetArtistDetailsUseCase)
//    // search
//    singleOf(::AddRecentSearchUseCase)
//    singleOf(::ClearAllRecentSearchesUseCase)
//    singleOf(::GetArtistsByQueryUseCase)
//    singleOf(::GetExploreMoreMovieUseCase)
//    singleOf(::GetMoviesByQueryUseCase)
//    singleOf(::GetPopularMoviesUseCase)
//    singleOf(::GetRecentSearchesUseCase)
//    singleOf(::GetRecommendedMovieUseCase)
//    singleOf(::GetSeriesByQueryUseCase)
//    singleOf(::RemoveRecentSearchUseCase)
//    // movies
//    singleOf(::GetMovieDetailsUseCase)
//    singleOf(::GetMovieReviewsUseCase)
//    singleOf(::GetMoviesByGenresUseCase)
//    singleOf(::GetMovieTopCastUseCase)
//    singleOf(::GetMovieTrailersUseCase)
//    singleOf(::GetSimilarMoviesUseCase)
//    singleOf(::GetMovieTrailersUseCase)
//
//    singleOf(::FilterMoviesByCategoryUseCase)
//    singleOf(::GetMovieGenresUseCase)
//    singleOf(::GetTopRatedMoviesUseCase)
//    singleOf(::GetRecommendedMovieUseCase)
//    singleOf(::GetUpcomingMovieUseCase)
//    singleOf(::GetNowPlayingMovieUseCase)
//    singleOf(::GetTrendingMoviesUseCase)
//    singleOf(::FilterMoviesByCategoryUseCase)
//    singleOf(::GetMovieGenresUseCase)
//    singleOf(::GetMoviesByGenreIdUseCase)
//    singleOf(::GetNowPlayingMovieUseCase)
//    singleOf(::GetUpcomingMovieUseCase)
//    // series
//    singleOf(::GetSeriesByGenresUseCase)
//    singleOf(::GetEpisodesForSeasonUseCase)
//    singleOf(::GetSeriesDetailsUseCase)
//    singleOf(::GetSeriesReviewsUseCase)
//    singleOf(::GetSeriesTopCastUseCase)
//    singleOf(::GetSeriesTrailersUseCase)
//    singleOf(::GetSimilarSeriesUseCase)
//    singleOf(::FilterSeriesByCategoryUseCase)
//    singleOf(::GetAiringTodaySeriesUseCase)
//    singleOf(::GetOnAirSeriesUseCase)
//    singleOf(::GetRecommendedSeriesUseCase)
//    singleOf(::GetSeriesByGenreIdUseCase)
//    singleOf(::GetSeriesGenresUseCase)
//    singleOf(::GetTopRatedSeriesUseCase)
//    singleOf(::GetRecommendedSeriesUseCase)
//    singleOf(::GetAiringTodaySeriesUseCase)
//    singleOf(::GetOnAirSeriesUseCase)
//
//    // user
//    singleOf(::LoginUseCase)
//    single<CoroutineDispatcher> { Dispatchers.IO }
//
//
//}