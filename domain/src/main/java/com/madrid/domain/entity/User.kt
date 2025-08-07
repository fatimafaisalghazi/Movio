package com.madrid.domain.entity
data class User(
    val id: String,
    val username: String,
    val profilePicUrl: String?,
    val isGuest: Boolean = false
)

data class UserList(
    val id: String,
    val name: String,
    val itemCount: Int = 0,
    val description: String = "",
    val posterUrl: String? = null,
    var isSelected: Boolean = false,
    var isLoading: Boolean = false
)

data class ListOperationStatus(
    val success: Boolean,
    val message: String
)