package com.madrid.data.dataSource.local.table.relationship

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.madrid.data.dataSource.local.table.MovieGenreEntity
import com.madrid.data.dataSource.local.table.MovieEntity

@Entity(tableName = "MovieGenreCrossRef", primaryKeys = ["genreId", "movieId"])
data class MovieGenreCrossRef(
    val genreId: Int,
    val movieId: Int
)

data class MovieWithGenres(
    @Embedded val movie: MovieEntity,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val genres: List<MovieGenreEntity>
)

data class GenreWithMovies(
    @Embedded val genre: MovieGenreEntity,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<MovieEntity>
)