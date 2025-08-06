package com.madrid.data.dataSource.mapper

import com.madrid.data.dataSource.remote.dto.list.ListOperationResponse
import com.madrid.data.dataSource.remote.dto.movie.ListDetailsResponse
import com.madrid.domain.entity.UserList

object ListMapper {

    fun ListOperationResponse.toDomainSuccess(): Boolean {
        return this.statusCode == 12 || this.statusCode == 1 || this.success == true
    }

    fun ListDetailsResponse.toDomain(): UserList {
        return UserList(
            id = this.id ?: "",
            name = this.name ?: "",
            itemCount = this.itemCount ?: 0,
            description = this.description ?: "",
            posterUrl = this.posterPath?.let {
                "https://image.tmdb.org/t/p/original$it"
            } ?: ""
        )
    }
}