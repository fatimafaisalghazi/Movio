package com.madrid.data.dataSource.remote.dto.list

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AddToListRequest(
    @SerializedName("media_id")
    val mediaId: Int
)