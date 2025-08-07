package com.madrid.domain.usecase.watchList

import com.madrid.domain.entity.WatchList
import com.madrid.domain.repository.ListRepository
import com.madrid.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetWatchListsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val listRepository: ListRepository
) {
    suspend operator fun invoke(): List<WatchList> {
        val sessionId = userRepository.getSessionId().first()
        return listRepository.getLists(sessionId)
    }
}