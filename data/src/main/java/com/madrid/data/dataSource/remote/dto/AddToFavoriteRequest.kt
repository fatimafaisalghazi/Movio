package com.madrid.data.dataSource.remote.dto

data class AddToFavoriteRequest(
    val media_type: String,
    val media_id: Int,
    val favorite: Boolean
)