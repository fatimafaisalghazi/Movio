package com.madrid.domain.repository

interface KeywordsSuggestionWordsRepository {
    suspend fun getSuggestionKeyWords(query: String) : List<String>
}