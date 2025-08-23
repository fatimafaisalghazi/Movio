package com.madrid.data.repositories

import com.madrid.data.repositories.remote.RemoteDataSource
import com.madrid.domain.repository.KeywordsSuggestionWordsRepository
import javax.inject.Inject

class KeywordsSuggestionWordsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : KeywordsSuggestionWordsRepository {
    override suspend fun getSuggestionKeyWords(query: String)  : List<String>{
        return  remoteDataSource.getSuggestionWords(query).results?.map {suggestWord->
            suggestWord?.name ?: ""
        } ?: emptyList()
    }
}