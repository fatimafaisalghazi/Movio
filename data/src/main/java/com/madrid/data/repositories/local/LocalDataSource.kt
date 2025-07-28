package com.madrid.data.repositories.local

import com.madrid.data.dataSource.local.table.ArtistEntity
import com.madrid.data.dataSource.local.table.MovieGenreEntity
import com.madrid.data.dataSource.local.table.MovieEntity
import com.madrid.data.dataSource.local.table.RecentSearchEntity
import com.madrid.data.dataSource.local.table.SeriesEntity
import com.madrid.data.dataSource.local.table.SeriesGenreEntity
import com.madrid.data.dataSource.local.table.relationship.GenreWithMovies
import com.madrid.data.dataSource.local.table.relationship.GenreWithSeries
import com.madrid.data.dataSource.local.table.relationship.MovieGenreCrossRef
import com.madrid.data.dataSource.local.table.relationship.SeriesGenreCrossRef

interface LocalDataSource {

    suspend fun insertMovie(movie: MovieEntity)
    suspend fun insertSeries(series: SeriesEntity)
    suspend fun insertArtist(artist: ArtistEntity)
    suspend fun insertMovieGenre(genre: MovieGenreEntity)
    suspend fun insertSeriesGenre(genre: SeriesGenreEntity)

    suspend fun getTopRatedMovies(): List<MovieEntity>

    suspend fun searchMovieByQueryFromDB(query: String, page: Int): List<MovieEntity>
    suspend fun searchSeriesByQueryFromDB(query: String, page: Int): List<SeriesEntity>
    suspend fun searchArtistByQueryFromDB(query: String, page: Int): List<ArtistEntity>

    suspend fun getRecentSearches(): List<RecentSearchEntity>
    suspend fun addRecentSearch(item: String)
    suspend fun removeRecentSearch(item: String)
    suspend fun clearAllRecentSearches()

    suspend fun relateMovieToGenre(movieGenreCrossRef: MovieGenreCrossRef)
    suspend fun increaseMovieGenreSeenCount(genreTitle: String)

    suspend fun relateSeriesToGenre(seriesGenreEntity: SeriesGenreCrossRef)
    suspend fun increaseSeriesGenreSeenCount(genreTitle: String)

    suspend fun getAllMovieGenres(): List<MovieGenreEntity>
    suspend fun getAllSeriesGenres(): List<SeriesGenreEntity>

    suspend fun getMoviesByGenres(): List<GenreWithMovies>
    suspend fun getSeriesByGenres(): List<GenreWithSeries>
}