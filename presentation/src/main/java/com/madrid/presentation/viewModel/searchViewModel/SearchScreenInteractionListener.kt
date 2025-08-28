package com.madrid.presentation.viewModel.searchViewModel

interface SearchScreenInteractionListener {
    fun onTopRatedTabClick()
    fun onMoviesTabClick()
    fun onSeriesTabClick()
    fun onActorTabClick()
    fun onClearSearchQueryClick()
    fun onSearchQueryChange(query: String)
    fun onMovieClick(movieId: Int)
    fun onSeriesClick(seriesId: Int)
    fun onTopResultClick(movieId: Int)
    fun onArtistClick(actorId: Int)
    fun onClearAll()
    fun onRemoveRecentSearchItem(recentSearchItem: String)
    fun onAddRecentSearch(recentSearch: String)
    fun onGetSuggestionKeywords()
    fun onDoAction()
    fun onChangeValueOfIsChangeSearchQuery()
    fun onSeeAllClick()
}