package com.madrid.presentation.screens.searchScreen.forYouAndExploreScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.modifier.removeWidthPaddingFromParent
import com.madrid.presentation.component.ShimmerEffectCard
import com.madrid.presentation.component.layout.NoInternetLayout
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.searchViewModel.SearchScreenState

fun LazyGridScope.forYouAndExploreSections(
    isVisible: Boolean,
    showSearchResults: Boolean,
    isLoading: Boolean,
    isError: Boolean,
    onClickSeeAll: () -> Unit,
    parentPadding: Dp,
    forYouMovies: List<SearchScreenState.MovieUiState>,
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    onMovieClick: (Int) -> Unit = {},
    onRetryButtonClick: () -> Unit = {}
) {
    if (!showSearchResults and isVisible) {
        forYouSection(
            isLoading = isLoading,
            isError = isError,
            onClickSeeAll = onClickSeeAll,
            parentPadding = parentPadding,
            forYouMovies = forYouMovies,
            onMovieClick = onMovieClick,
            onRetryButtonClick = onRetryButtonClick
        )

        exploreMoreSection(
            exploreMoreMovies = exploreMoreMovies,
            onMovieClick = onMovieClick,
            onRetryButtonClick = onRetryButtonClick
        )
    }
}

private fun LazyGridScope.forYouSection(
    isLoading: Boolean,
    isError: Boolean,
    onClickSeeAll: () -> Unit,
    parentPadding: Dp,
    forYouMovies: List<SearchScreenState.MovieUiState>,
    onMovieClick: (Int) -> Unit = {},
    onRetryButtonClick: () -> Unit = {},
) {
    when {
        isLoading -> {
            items(12) { ShimmerEffectCard(modifier = Modifier.padding(bottom = 12.dp)) }
        }

        isError -> {
            item(span = { GridItemSpan(currentLineSpan = maxLineSpan) }) { NoInternetLayout(onRetryButtonClick) }
        }

        forYouMovies.isNotEmpty() -> {
            forYouSectionResult(
                onClickSeeAll = onClickSeeAll,
                parentPadding = parentPadding,
                forYouMovies = forYouMovies,
                onMovieClick = onMovieClick
            )
        }
    }
}

private fun LazyGridScope.forYouSectionResult(
    onClickSeeAll: () -> Unit,
    parentPadding: Dp,
    forYouMovies: List<SearchScreenState.MovieUiState>,
    onMovieClick: (Int) -> Unit = {},
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        CustomTextTitle(
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp),
            primaryText = stringResource(com.madrid.presentation.R.string.for_u),
            secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
            endIcon = painterResource(R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onClickSeeAll() }
        )
    }
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        LazyRow(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .removeWidthPaddingFromParent(parentPadding = parentPadding),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(forYouMovies) { movie ->
                MovioVerticalCard(
                    modifier = Modifier.size(width = 124.dp, height = 202.dp),
                    description = movie.title,
                    movieImage = movie.imageUrl,
                    rate = movie.rating,
                    imageHeight = 160.dp,
                    onClick = { onMovieClick(movie.id) }
                )
            }
        }
    }
}

private fun LazyGridScope.exploreMoreSection(
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    onMovieClick: (Int) -> Unit = {},
    onRetryButtonClick: () -> Unit = {},
) {
    when {
        exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.Loading -> {
            items(12) {
                ShimmerEffectCard(modifier = Modifier.padding(bottom = 12.dp))
            }
        }

        exploreMoreMovies.itemCount == 0 && exploreMoreMovies.loadState.refresh is LoadState.Error -> {
            item(span = { GridItemSpan(maxLineSpan) }) {
                NoInternetLayout(onRetryButtonClick)
            }
        }

        exploreMoreMovies.itemCount > 0 -> {
            exploreMoreSectionResult(
                exploreMoreMovies = exploreMoreMovies,
                onMovieClick = onMovieClick
            )
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
private fun LazyGridScope.exploreMoreSectionResult(
    exploreMoreMovies: LazyPagingItems<SearchScreenState.MovieUiState>,
    onMovieClick: (Int) -> Unit = {},
) {
    item(
        span = { GridItemSpan(maxLineSpan) }
    ) {
        CustomTextTitle(
            modifier = Modifier.padding(bottom = 12.dp),
            primaryText = stringResource(com.madrid.presentation.R.string.explore_more)
        )
    }
    items(count = exploreMoreMovies.itemCount) { index ->
        val movie = exploreMoreMovies[index] ?: return@items
        BoxWithConstraints {
            val cardWidth = maxWidth
            val cardHeight = cardWidth * (180f / 158)
            MovioVerticalCard(
                modifier = Modifier
                    .width(cardWidth)
                    .padding(bottom = 12.dp),
                description = movie.title,
                movieImage = movie.imageUrl,
                rate = movie.rating,
                imageHeight = cardHeight,
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}
