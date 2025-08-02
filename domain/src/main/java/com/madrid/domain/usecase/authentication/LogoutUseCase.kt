package com.madrid.domain.usecase.authentication

import com.madrid.domain.repository.UserRepository

class LogoutUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute() {
        userRepository.logout()
    }
}