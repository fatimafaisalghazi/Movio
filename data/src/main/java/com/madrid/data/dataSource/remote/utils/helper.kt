package com.madrid.data.dataSource.remote.utils

import com.madrid.domain.entity.Cast
import com.madrid.domain.entity.Movie
import kotlin.Int

fun getDefaultMovie(): Movie{
    return Movie(
        id = 0,
        title = "",
        imageUrl = "",
        rate = 0.0,
        yearOfRelease = "",
        description = "",
        genre = listOf(),
        movieDuration = "",
        crew = listOf(),
        profilePage = " "
    )
}