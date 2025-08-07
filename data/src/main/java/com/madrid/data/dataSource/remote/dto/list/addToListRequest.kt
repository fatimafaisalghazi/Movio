package com.madrid.data.dataSource.remote.dto.list

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddToListRequest(
    @SerializedName("media_id")
    val mediaId: Int
)

data class MovieListBody(
    val name: String,
    val description: String,
    val language: String
)


data class CreateListResponse(
    val success: Boolean,
    val status_code: Int,
    val status_message: String,
    val list_id: Int
)