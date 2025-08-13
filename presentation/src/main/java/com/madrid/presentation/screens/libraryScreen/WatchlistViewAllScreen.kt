package com.madrid.presentation.screens.libraryScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.FloatingButton
import com.madrid.designSystem.component.TopAppBar
import com.madrid.presentation.component.videoLibrary.VideoLibrary
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.addtolist.CreateListBottomSheet
import com.madrid.presentation.screens.addtolist.SuccessNotificationRow
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchListViewAllInteractionListener
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.watchlistViewAll.WatchlistViewAllViewModel
import com.madrid.presentation.R as presentationR

@Composable
fun WatchlistViewAllScreen(
    viewModel: WatchlistViewAllViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is WatchlistViewAllEffect.NavigateBack -> {
                    navController.navigateUp()
                }

                is WatchlistViewAllEffect.NavigateToDetails -> {
                    navController.navigate(
                        Destinations.WatchListDetailsScreen(
                            watchListId = effect.watchListId,
                            watchListTitle = effect.watchListTitle
                        )
                    )
                }
            }
        }
    }

    val interactionListener = viewModel as WatchListViewAllInteractionListener
    WatchlistViewAllScreenContent(
        interactionListener = interactionListener,
        state = state
    )
}

@Composable
fun WatchlistViewAllScreenContent(
    interactionListener: WatchListViewAllInteractionListener,
    state: WatchlistViewAllUiState
) {
    Column(
        modifier = Modifier.statusBarsPadding()
    ) {
        TopAppBar(
            text = stringResource(presentationR.string.watchlist),
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { interactionListener.onBackButtonClicked() },
            modifier = Modifier.padding(
                horizontal = 16.dp
            )
        )

        AnimatedVisibility(
            visible = state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) { LoadingContent() }

        AnimatedVisibility(
            visible = state.isLoading.not() && state.watchLists.isEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            EmptyListContent()
        }
        AnimatedVisibility(
            visible = state.isLoading.not() && state.watchLists.isEmpty().not(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            WatchListsGrid(interactionListener, state)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        FloatingButton(
            onClick = interactionListener::onAddButtonClicked,
            size = 60,
            icon = painterResource(id = R.drawable.add),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )

        SuccessNotificationRow(
            isVisible = state.showSnackBar,
            message = stringResource(state.snackBarMessage),
            icon = painterResource(id = R.drawable.archive_tick),
            onDismiss = interactionListener::onDismissSnackBar,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )
    }

    CreateListBottomSheet(
        show = state.showCreateListBottomSheet,
        onCreateClick = interactionListener::onCreateButtonClicked,
        onDismiss = interactionListener::dismissCreateListBottomSheet,
    )

}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 158.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
        ) {
            items(12) { index ->
                Box(
                    modifier = Modifier
                        .shimmerEffect()
                        .size(158.dp, 220.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                )
            }
        }
    }
}

@Composable
private fun EmptyListContent() {
    EmptySearchLayout(
        title = "\uD83C\uDFAC Nothing here yet!",
        description = "Add movies and TV shows to build your personal watchlist. The perfect binge starts here!",
        image = R.drawable.empty,
        imageSize = 180,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun WatchListsGrid(
    interactionListener: WatchListViewAllInteractionListener,
    state: WatchlistViewAllUiState
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 158.dp),
        verticalArrangement = Arrangement.Top,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(state.watchLists.size) { index ->
            val watchlist = state.watchLists[index]
            VideoLibrary(
                onClick = { interactionListener.onItemClick(watchlist) },
                videosNumber = watchlist.numberOfVideos,
                title = watchlist.watchListTitle,
                posterUrl = watchlist.posterUrl
            )
        }
    }
}


private fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(1000)),
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFF2A2445),
                Color(0xFF3D3660),
                Color(0xFF2A2445),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}