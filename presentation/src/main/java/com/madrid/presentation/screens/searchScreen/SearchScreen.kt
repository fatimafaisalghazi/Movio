package com.madrid.presentation.screens.searchScreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.emptyRecentSearch
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.refreshScreenHolder.RefreshScreenHolder
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.FilterSearchScreen
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.ForYouAndExploreScreen
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.recentSearchScreen
import com.madrid.presentation.screens.searchScreen.utils.SearchSections
import com.madrid.presentation.screens.searchScreen.utils.highlightCharactersInText
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenUiEffect
import com.madrid.presentation.viewModel.searchViewModel.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current


    HandleNavigation(
        navController = navController,
        effect = viewModel.effect
    )

    RefreshScreenHolder(
        refreshState = uiState.searchUiState.refreshState,
        onRefresh = { viewModel.onRefresh(uiState.selectedSearchSection) }
    ) {
        ContentSearchScreen(
            uiState = uiState,
            listener = viewModel
        )

        if (uiState.searchUiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                MovioIcon(
                    painter = painterResource(R.drawable.loading),
                    contentDescription = "Loading",
                    tint = Theme.color.brand.primary
                )
            }
        }
    }
}

@Composable
private fun HandleNavigation(
    navController: NavController,
    effect: Flow<SearchScreenUiEffect>
) {
    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is SearchScreenUiEffect.NavigateToMovieDetails -> {
                    navController.navigate(Destinations.MovieDetailsScreen(effect.movieId))
                }

                is SearchScreenUiEffect.NavigateToSeriesDetails -> {
                    navController.navigate(
                        Destinations.SeriesDetailsScreen(
                            effect.seriesId,
                            effect.seasonNumber
                        )
                    )
                }

                is SearchScreenUiEffect.NavigateToActorDetails -> {
                    navController.navigate(Destinations.ActorDetails(effect.actorId))
                }

                is SearchScreenUiEffect.NavigateToSeeAllScreen -> {
                    navController.navigate(Destinations.SeeAllForYouScreen)
                }

                is SearchScreenUiEffect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
fun ContentSearchScreen(
    uiState: SearchScreenState,
    listener: SearchScreenInteractionListener
) {

    val topRated = uiState.filteredScreenUiState.topResult.collectAsLazyPagingItems()
    val movies = uiState.filteredScreenUiState.movie.collectAsLazyPagingItems()
    val series = uiState.filteredScreenUiState.series.collectAsLazyPagingItems()
    val artist = uiState.filteredScreenUiState.artist.collectAsLazyPagingItems()
    val exploreMoreMovies = uiState.searchUiState.exploreMoreMovies.collectAsLazyPagingItems()

    val showSearchResults = uiState.searchUiState.searchQuery.isNotBlank()
    var selectedTabIndex by remember { mutableStateOf(SearchSections.MOVIES) }
    var isPressSearchIconInKeyboard by remember { mutableIntStateOf(0) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var isWrite by remember { mutableStateOf(false) }

    BackHandler(enabled = isFocused || uiState.searchUiState.searchQuery.isNotEmpty()) {
        listener.onClearSearchQueryClick()
        focusManager.clearFocus(force = true)
        isFocused = false
    }

    LaunchedEffect(uiState.isDoAction) {
        if (isPressSearchIconInKeyboard == 1) {
            isFocused = false
            isPressSearchIconInKeyboard = 0
        } else {
            isFocused = true
            snapshotFlow { uiState.searchUiState.searchQuery }
                .debounce(1200)
                .collect { query ->
                    isWrite = false
                    if (query.isNotBlank() && uiState.searchUiState.isSearchQueryChange) {
                        val res = listener.onGetSuggestionKeywords()
                        Log.e("MY_TAG", " the result size $res")
                    }
                }
        }

    }
    LaunchedEffect(Unit) {
        isFocused = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 158.dp),
            modifier = Modifier.background(Theme.color.surfaces.surface),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                BasicTextInputField(
                    value = uiState.searchUiState.searchQuery,
                    onValueChange = { newQuery ->
                        isWrite = true
                        listener.onSearchQueryChange(newQuery)
                    },
                    borderBrushColors = null,
                    hintText = stringResource(com.madrid.presentation.R.string.searchdot),
                    startIconPainter = painterResource(R.drawable.search_normal),
                    endIconPainter = painterResource(R.drawable.outline_add),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    onClickEndIcon = { listener.onClearSearchQueryClick() },
                    onClickInputTextField = { isFocused = true },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            isPressSearchIconInKeyboard = 1
                            isFocused = false
                            listener.onDoAction()
                            if (uiState.searchUiState.searchQuery.isNotBlank() && uiState.searchUiState.isSearchQueryChange) {

                                listener.onChangeValueOfIsChangeSearchQuery()
                                listener.onAddRecentSearch(uiState.searchUiState.searchQuery)
                                when (uiState.selectedSearchSection) {
                                    SearchSections.TOP_RATED -> listener.onTopRatedTabClick()
                                    SearchSections.MOVIES -> listener.onMoviesTabClick()
                                    SearchSections.SERIES -> listener.onSeriesTabClick()
                                    SearchSections.ARTISTS -> listener.onActorTabClick()
                                }
                            }
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                )
            }

            if (uiState.searchUiState.searchQuery.isEmpty() && isFocused != true) {
                ForYouAndExploreScreen(
                    showSearchResults = showSearchResults,
                    isLoading = uiState.searchUiState.isLoading,
                    isError = uiState.searchUiState.isError,
                    forYouMovies = uiState.searchUiState.forYouMovies,
                    onMovieClick = { listener.onMovieClick(it.id.toInt()) },
                    exploreMoreMovies = exploreMoreMovies,
                    onClickSeeAll = { listener.onSeeAllClick() },
                    parentPadding = 16.dp
                )
            }

            if (isFocused && uiState.recentSearchUiState.isNotEmpty()) {
                recentSearchScreen(
                    searchHistory = uiState.recentSearchUiState,
                    searchQuery = uiState.searchUiState.searchQuery,
                    onSearchItemClick = { itemInRecent ->
                        listener.onSearchQueryChange(itemInRecent)
                        isFocused = false
                        listener.onDoAction()
                        listener.onChangeValueOfIsChangeSearchQuery()
                        listener.onAddRecentSearch(itemInRecent)
                        when (uiState.selectedSearchSection) {
                            SearchSections.TOP_RATED -> listener.onTopRatedTabClick()
                            SearchSections.MOVIES -> listener.onMoviesTabClick()
                            SearchSections.SERIES -> listener.onSeriesTabClick()
                            SearchSections.ARTISTS -> listener.onActorTabClick()
                        }

                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    onRemoveItem = { listener.onRemoveRecentSearchItem(it) },
                    onClearAll = { listener.onClearAll() },
                    highlightCharactersInText = ::highlightCharactersInText,
                    isWrite = isWrite,
                    onSearchItem = { itemInRecent ->
                        listener.onSearchQueryChange(itemInRecent)
                        isPressSearchIconInKeyboard = 1
                        isFocused = false
                        listener.onDoAction()
                        listener.onChangeValueOfIsChangeSearchQuery()
                        listener.onAddRecentSearch(itemInRecent)
                        when (uiState.selectedSearchSection) {
                            SearchSections.TOP_RATED -> listener.onTopRatedTabClick()
                            SearchSections.MOVIES -> listener.onMoviesTabClick()
                            SearchSections.SERIES -> listener.onSeriesTabClick()
                            SearchSections.ARTISTS -> listener.onActorTabClick()
                        }

                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    sizeOfSuggestionKeywords = uiState.suggestionListSize
                )
            }
            if (isFocused && uiState.recentSearchUiState.isEmpty()) {
                emptyRecentSearch()
            }
        }

        if (uiState.searchUiState.searchQuery.isNotEmpty() && isFocused != true) {

            FilterSearchScreen(
                typeOfFilterSearch = uiState.selectedSearchSection,
                topRated = topRated,
                movies = movies,
                series = series,
                artist = artist,
                selectedTabIndex = selectedTabIndex,

                onChangeSelectedTabIndex = { newIndex ->
                    if (newIndex != selectedTabIndex) {
                        selectedTabIndex = newIndex
                        when (newIndex) {
                            SearchSections.TOP_RATED -> listener.onTopRatedTabClick()
                            SearchSections.MOVIES -> listener.onMoviesTabClick()
                            SearchSections.SERIES -> listener.onSeriesTabClick()
                            SearchSections.ARTISTS -> listener.onActorTabClick()
                        }


                    }
                },
                onChangeTypeFilterSearch = {},
                onSeriesClick = { seriesId ->
                    listener.onSeriesClick(seriesId)
                },
                onMovieClick = { moviesId ->
                    listener.onMovieClick(moviesId)
                },
                onActorClick = { actorId ->
                    listener.onArtistClick(actorId)
                },
                onTopResultClick = { movieId ->
                    listener.onTopResultClick(movieId)
                }
            )
        }
    }

}