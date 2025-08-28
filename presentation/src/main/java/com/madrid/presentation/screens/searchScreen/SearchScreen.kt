package com.madrid.presentation.screens.searchScreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.madrid.designSystem.component.textInputField.BasicTextInputField
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.emptyRecentSearch
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.refreshScreenHolder.RefreshScreenHolder
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.FilterSearchScreen
import com.madrid.presentation.screens.searchScreen.features.recentSearchLayout.recentSearchScreen
import com.madrid.presentation.screens.searchScreen.forYouAndExploreScreen.forYouAndExploreSections
import com.madrid.presentation.screens.searchScreen.utils.SearchSections
import com.madrid.presentation.screens.searchScreen.utils.highlightCharactersInText
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenInteractionListener
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
                    navController.navigate(route = Destinations.MovieDetailsScreen(movieId = effect.movieId))
                }

                is SearchScreenUiEffect.NavigateToSeriesDetails -> {
                    navController.navigate(
                        route = Destinations.SeriesDetailsScreen(
                            seriesId = effect.seriesId,
                            seasonNumber = effect.seasonNumber
                        )
                    )
                }

                is SearchScreenUiEffect.NavigateToActorDetails -> {
                    navController.navigate(route = Destinations.ActorDetails(artistId = effect.actorId))
                }

                is SearchScreenUiEffect.NavigateToSeeAllScreen -> {
                    navController.navigate(route = Destinations.SeeAllForYouScreen)
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
                .debounce(1000)
                .collect { query ->
                    isWrite = false
                    if (query.isNotBlank() && uiState.searchUiState.isSearchQueryChange) {
                        listener.onGetSuggestionKeywords()
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
                    hintText = stringResource(com.madrid.presentation.R.string.searchdot),
                    startIconPainter = painterResource(R.drawable.search_normal),
                    endIconPainter = painterResource(R.drawable.outline_add),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    onClickEndIcon = { listener.onClearSearchQueryClick() },
                    onClickInputTextField = { isFocused = true },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            isPressSearchIconInKeyboard = 1
                            isFocused = false
                            if (uiState.searchUiState.searchQuery.isNotBlank() && uiState.searchUiState.isSearchQueryChange) {
                                onSearch(
                                    itemInRecent = uiState.searchUiState.searchQuery,
                                    selectedSearchSection = uiState.selectedSearchSection,
                                    listener = listener
                                )
                            }
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )
                )
            }

            forYouAndExploreSections(
                isVisible = uiState.searchUiState.searchQuery.isEmpty() && !isFocused,
                showSearchResults = uiState.searchUiState.searchQuery.isNotBlank(),
                isLoading = uiState.searchUiState.isLoading,
                isError = uiState.searchUiState.isError,
                forYouMovies = uiState.searchUiState.forYouMovies,
                onMovieClick = listener::onMovieClick,
                exploreMoreMovies = exploreMoreMovies,
                onClickSeeAll = listener::onSeeAllClick,
                parentPadding = 16.dp
            )

            recentSearchScreen(
                isVisible = isFocused && uiState.recentSearchUiState.isNotEmpty(),
                searchHistory = uiState.recentSearchUiState,
                searchQuery = uiState.searchUiState.searchQuery,
                onSearchItemClick = { itemInRecent ->
                    listener.onSearchQueryChange(itemInRecent)
                    isFocused = false
                    onSearch(itemInRecent,
                        selectedSearchSection = uiState.selectedSearchSection, listener)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                onRemoveItem = listener::onRemoveRecentSearchItem,
                onClearAll = listener::onClearAll,
                highlightCharactersInText = ::highlightCharactersInText,
                isWrite = isWrite,
                onSearchItem = { itemInRecent ->
                    listener.onSearchQueryChange(itemInRecent)
                    isPressSearchIconInKeyboard = 1
                    isFocused = false
                    onSearch(itemInRecent, uiState.selectedSearchSection, listener)
                    keyboardController?.hide()
                    focusManager.clearFocus()
                },
                sizeOfSuggestionKeywords = uiState.suggestionListSize
            )
            if (isFocused && uiState.recentSearchUiState.isEmpty()) {
                emptyRecentSearch()
            }
        }


        FilterSearchScreen(
            isVisible = uiState.searchUiState.searchQuery.isNotEmpty() && !isFocused,
            typeOfFilterSearch = uiState.selectedSearchSection,
            topRated = topRated,
            movies = movies,
            series = series,
            artist = artist,
            selectedTabIndex = uiState.selectedSearchSection,
            onChangeSelectedTabIndex = { searchSection ->
                if (searchSection != uiState.selectedSearchSection) {
                    when (searchSection) {
                        SearchSections.TOP_RATED -> listener::onTopRatedTabClick
                        SearchSections.MOVIES -> listener::onMoviesTabClick
                        SearchSections.SERIES -> listener::onSeriesTabClick
                        SearchSections.ARTISTS -> listener::onActorTabClick
                    }
                }
            },
            onSeriesClick = listener::onSeriesClick,
            onMovieClick = listener::onMovieClick,
            onActorClick = listener::onActorClick,
            onTopResultClick = listener::onTopResultClick
        )
    }
}


private fun onSearch(
    itemInRecent: String,
    selectedSearchSection: SearchSections,
    listener: SearchScreenInteractionListener
) {
    listener.onDoAction()
    listener.onChangeValueOfIsChangeSearchQuery()
    listener.onAddRecentSearch(recentSearch = itemInRecent)
    when (selectedSearchSection) {
        SearchSections.TOP_RATED -> listener::onTopRatedTabClick
        SearchSections.MOVIES -> listener::onMoviesTabClick
        SearchSections.SERIES -> listener::onSeriesTabClick
        SearchSections.ARTISTS -> listener::onActorTabClick
    }
}