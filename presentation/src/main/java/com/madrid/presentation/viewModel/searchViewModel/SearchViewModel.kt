package com.madrid.presentation.viewModel.searchViewModel

import androidx.paging.PagingData
import androidx.paging.map
import com.madrid.domain.entity.Artist
import com.madrid.domain.entity.Movie
import com.madrid.domain.usecase.search.AddRecentSearchUseCase
import com.madrid.domain.usecase.search.ClearAllRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetArtistsByQueryUseCase
import com.madrid.domain.usecase.search.GetExploreMoreMovieUseCase
import com.madrid.domain.usecase.search.GetMoviesByQueryUseCase
import com.madrid.domain.usecase.search.GetRecentSearchesUseCase
import com.madrid.domain.usecase.search.GetRecommendedMovieUseCase
import com.madrid.domain.usecase.search.GetSeriesByQueryUseCase
import com.madrid.domain.usecase.search.RemoveRecentSearchUseCase
import com.madrid.domain.usecase.suggestKeywords.GetSuggestionKeywordsUseCase
import com.madrid.presentation.pagination.ExplorePagingSource
import com.madrid.presentation.pagination.SearchArtistPagingSource
import com.madrid.presentation.pagination.SearchMoviePagingSource
import com.madrid.presentation.pagination.SearchSeriesPagingSource
import com.madrid.presentation.screens.searchScreen.utils.SearchSections
import com.madrid.presentation.viewModel.base.BaseViewModel
import com.madrid.presentation.viewModel.base.ErrorState
import com.madrid.presentation.viewModel.uiStateMapper.toArtistUiState
import com.madrid.presentation.viewModel.uiStateMapper.toMovieUiState
import com.madrid.presentation.viewModel.uiStateMapper.toSeriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getArtistsByQueryUseCase: GetArtistsByQueryUseCase,
    private val getMoviesByQueryUseCase: GetMoviesByQueryUseCase,
    private val getSeriesByQueryUseCase: GetSeriesByQueryUseCase,
    private val getRecommendedMovieUseCase: GetRecommendedMovieUseCase,
    private val getExploreMoreMovieUseCase: GetExploreMoreMovieUseCase,
    private val getRecentSearchesUseCase: GetRecentSearchesUseCase,
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val removeRecentSearchUseCase: RemoveRecentSearchUseCase,
    private val clearAllRecentSearchesUseCase: ClearAllRecentSearchesUseCase,
    private val getSuggestionKeywordsUseCase: GetSuggestionKeywordsUseCase
) : BaseViewModel<SearchScreenState, SearchScreenUiEffect>(
    SearchScreenState()
), SearchScreenInteractionListener {

    init {
        loadRecentSearches()
        loadInitialData()
    }

    private fun loadRecentSearches() {
        tryToExecute(
            function = getRecentSearchesUseCase::invoke,
            onSuccess = ::onSuccessUpdateRecent,
        )
    }

    private fun onSuccessUpdateRecent(result: List<String>) {
        updateState { searchScreenState ->
            searchScreenState.copy(allRecentSearchTexts = result, suggestionListSize = 0)
        }
        onChangeRecentSearchUiState()
    }

    override fun onGetSuggestionKeywords() {
        val query = state.value.searchUiState.searchQuery
        if (query.isNotBlank() && query.isNotEmpty()) {
            tryToExecute(
                function = { getSuggestionKeywordsUseCase.invoke(query.trim()) },
                onSuccess = ::onSuccessGetSuggestionKeywords,
            )
        }
    }

    private fun onSuccessGetSuggestionKeywords(suggestionKeyWords: List<String>) {
        updateState { searchScreenState ->
            searchScreenState.copy(
                allRecentSearchTexts = suggestionKeyWords + searchScreenState.allRecentSearchTexts,
                suggestionListSize = suggestionKeyWords.size
            )
        }
        onChangeRecentSearchUiState()
    }

    override fun onSearchQueryChange(query: String) {
        updateState { searchScreenState ->
            searchScreenState.copy(
                isDoAction = !searchScreenState.isDoAction,
                searchUiState = searchScreenState.searchUiState.copy(
                    searchQuery = query,
                    isSearchQueryChange = true
                )
            )
        }
        loadRecentSearches()
        onChangeRecentSearchUiState()
    }

    override fun onClearSearchQueryClick() {
        updateState { searchScreenState ->
            searchScreenState.copy(
                searchUiState = searchScreenState.searchUiState.copy(
                    searchQuery = "",
                )
            )
        }
        loadRecentSearches()
    }

    override fun onAddRecentSearch(recentSearch: String) {
        tryToExecute(
            function = {
                addRecentSearchUseCase.invoke(item = recentSearch)
                getRecentSearchesUseCase.invoke()
            },
            onSuccess = ::onSuccessUpdateRecent,
        )
    }

    override fun onClearAll() {
        updateState { searchScreenStat ->
            searchScreenStat.copy(
                isDoAction = !searchScreenStat.isDoAction
            )
        }

        tryToExecute(
            function = {
                clearAllRecentSearchesUseCase.invoke()
                getRecentSearchesUseCase.invoke()
            },
            onSuccess = ::onSuccessUpdateRecent,
        )
    }

    override fun onRemoveRecentSearchItem(recentSearchItem: String) {
        onDoAction()
        tryToExecute(
            function = {
                removeRecentSearchUseCase.invoke(recentSearchItem)
                getRecentSearchesUseCase.invoke()
            },
            onSuccess = ::onSuccessUpdateRecent,
            onError = ::onError,
        )
    }

    override fun onChangeValueOfIsChangeSearchQuery() {
        updateState { searchScreenState ->
            searchScreenState.copy(
                searchUiState = searchScreenState.searchUiState.copy(
                    isSearchQueryChange = false
                )
            )
        }
    }

    override fun onSeeAllClick() {
        emitNewEffect(effect = SearchScreenUiEffect.NavigateToSeeAllScreen)
    }

    private fun loadInitialData() {
        getRecommendedMovies()
        getExploreMoreMovies()
    }

    private fun getRecommendedMovies() {
        tryToExecute(
            function = { getRecommendedMovieUseCase.invoke(page = 1) },
            onSuccess = ::onSuccessGetRecommendedMovies,
            onError = ::onError,
        )
    }

    private fun onSuccessGetRecommendedMovies(result: List<Movie>) {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(
                    forYouMovies = result.map { movie -> movie.toMovieUiState() },
                    isLoading = false,
                    refreshState = false
                )
            )
        }
    }

    private fun getExploreMoreMovies() {
        launchPagingRequest(
            pagingSourceFactory = { ExplorePagingSource(getExploreMoreMovieUseCase) },
            onSuccess = ::onSuccessGetExploreMoreMovies,
            onStartLoading = ::onStartLoading,
            onError = ::onErrorPaging
        )
    }

    private fun onSuccessGetExploreMoreMovies(pagingFlow: Flow<PagingData<Movie>>) {
        val result = pagingFlow.map { pagingData -> pagingData.map { it.toMovieUiState() } }

        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(
                    exploreMoreMovies = result,
                    isLoading = false,
                    refreshState = false
                )
            )
        }
    }

    private fun onStartLoading() {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(
                    isLoading = true,
                )
            )
        }
    }

    private fun onErrorPaging(throwValue: Throwable) {
        updateState { searchScreenState ->
            searchScreenState.copy(
                searchUiState = searchScreenState.searchUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwValue.message
                )
            )
        }
    }

    private fun onError(throwValue: ErrorState) {
        updateState { searchScreenState ->
            searchScreenState.copy(
                searchUiState = searchScreenState.searchUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwValue.message
                )
            )
        }
    }

    override fun onMoviesTabClick() {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(isLoading = true),
                selectedSearchSection = SearchSections.MOVIES
            )
        }
        launchPagingRequest(
            pagingSourceFactory = {
                SearchMoviePagingSource(
                    state.value.searchUiState.searchQuery,
                    getMoviesByQueryUseCase
                )
            },
            onSuccess = ::onSuccessSearchFilteredMovies,
            onError = ::onErrorPaging
        )
    }

    private fun onSuccessSearchFilteredMovies(pagingFlow: Flow<PagingData<Movie>>) {
        val result = pagingFlow.map { pagingData -> pagingData.map { it.toMovieUiState() } }

        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    movie = result,
                    isLoading = false
                ),
                searchUiState = current.searchUiState.copy(isLoading = false)
            )
        }
    }

    override fun onSeriesTabClick() {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(isLoading = true),
                selectedSearchSection = SearchSections.SERIES
            )
        }
        launchPagingRequest(
            pagingSourceFactory = {
                SearchSeriesPagingSource(
                    state.value.searchUiState.searchQuery,
                    getSeriesByQueryUseCase
                )
            },
            onSuccess = { pagingFlow ->
                val result = pagingFlow.map { pagingData ->
                    pagingData.map { it.toSeriesUiState() }
                }
                (::onUpdateSeriesSearch)(result)
            }
        )
    }

    override fun onTopRatedTabClick() {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(isLoading = true),
                selectedSearchSection = SearchSections.TOP_RATED
            )
        }
        getTopRatedMovies()
    }

    private fun getTopRatedMovies() {
        launchPagingRequest(
            pagingSourceFactory = {
                SearchMoviePagingSource(
                    query = state.value.searchUiState.searchQuery,
                    getMoviesByQueryUseCase = getMoviesByQueryUseCase
                )
            },
            onSuccess = ::onSuccessGetTopRatedMovies
        )
    }

    private fun onSuccessGetTopRatedMovies(pagingFlow: Flow<PagingData<Movie>>) {
        val result = pagingFlow.map { pagingData -> pagingData.map { it.toMovieUiState() } }

        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    topResult = result,
                    isLoading = false
                ),
                searchUiState = current.searchUiState.copy(isLoading = false)
            )
        }
    }


    override fun onActorTabClick() {
        updateState {
            it.copy(
                searchUiState = it.searchUiState.copy(isLoading = true),
                selectedSearchSection = SearchSections.ARTISTS
            )
        }
        launchPagingRequest(
            pagingSourceFactory = {
                SearchArtistPagingSource(
                    state.value.searchUiState.searchQuery,
                    getArtistsByQueryUseCase
                )
            },
            onSuccess = ::onSuccessClickActorTab
        )
    }

    private fun onSuccessClickActorTab(pagingFlow: Flow<PagingData<Artist>>) {
        val result = pagingFlow.map { pagingData ->
            pagingData.map { it.toArtistUiState() }
        }

        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    artist = result,
                    isLoading = false
                ),
                searchUiState = current.searchUiState.copy(isLoading = false)
            )
        }
    }

    private fun onChangeRecentSearchUiState() {
        updateState { searchScreenState ->
            searchScreenState.copy(
                recentSearchUiState = if (searchScreenState.searchUiState.searchQuery.isEmpty() ||
                    searchScreenState.searchUiState.searchQuery.isBlank()
                ) {
                    searchScreenState.allRecentSearchTexts
                } else {
                    val queryWords = searchScreenState
                        .searchUiState
                        .searchQuery
                        .trim()
                        .lowercase()
                        .split(" ")
                        .map { it.trim() }
                        .filter { it.isNotEmpty() }

                    searchScreenState
                        .allRecentSearchTexts
                        .filter { recentText ->
                            queryWords.all { recentText.contains(it) }
                        }
                }
            )
        }
    }

    private fun onUpdateSeriesSearch(result: Flow<PagingData<SearchScreenState.SeriesUiState>>) {
        updateState { current ->
            current.copy(
                filteredScreenUiState = current.filteredScreenUiState.copy(
                    series = result,
                    isLoading = false
                ),
                searchUiState = current.searchUiState.copy(isLoading = false)
            )
        }
    }

    fun onRefresh(
        typeOfFilterSearch: SearchSections
    ) {
        onStartRefresh()
        if (state.value.searchUiState.searchQuery.isEmpty()) {
            getRecommendedMovies()
            getExploreMoreMovies()
        } else {
            when (typeOfFilterSearch) {
                SearchSections.TOP_RATED -> onTopRatedTabClick()
                SearchSections.MOVIES -> onMoviesTabClick()
                SearchSections.SERIES -> onSeriesTabClick()
                SearchSections.ARTISTS -> onActorTabClick()
            }
            onFinishRefresh()
        }
    }

    private fun onStartRefresh() {
        updateState { current ->
            current.copy(
                searchUiState = current.searchUiState.copy(
                    refreshState = true,
                    errorMessage = null,
                    isError = false
                )
            )
        }
    }

    private fun onFinishRefresh() {
        updateState { current ->
            current.copy(
                searchUiState = current.searchUiState.copy(
                    refreshState = false,
                )
            )
        }
    }

    override fun onDoAction() {
        updateState { searchScreenStat ->
            searchScreenStat.copy(
                isDoAction = !searchScreenStat.isDoAction
            )
        }
    }

    override fun onMovieClick(movieId: Int) {
        emitNewEffect(effect = SearchScreenUiEffect.NavigateToMovieDetails(movieId))
    }

    override fun onSeriesClick(seriesId: Int) {
        emitNewEffect(effect = SearchScreenUiEffect.NavigateToSeriesDetails(seriesId, 1))
    }

    override fun onTopResultClick(movieId: Int) {
        emitNewEffect(effect = SearchScreenUiEffect.NavigateToMovieDetails(movieId))
    }

    override fun onActorClick(actorId: Int) {
        emitNewEffect(effect = SearchScreenUiEffect.NavigateToActorDetails(actorId))
    }

}