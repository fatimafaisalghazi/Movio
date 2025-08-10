package com.madrid.presentation.screens.libraryScreen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllEffect
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllUiState
import com.madrid.presentation.viewModel.libraryViewModel.viewAll.ViewAllViewModel
import com.madrid.presentation.viewModel.shared.MediaType

@Composable
fun ViewAllScreen(
    viewModel: ViewAllViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val navController = LocalNavController.current
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ViewAllEffect.NavigateBack -> {
                    navController.navigateUp()
                }

                is ViewAllEffect.NavigateToDetails -> {
                    if (effect.mediaType == MediaType.MOVIE) {
                        navController.navigate(
                            Destinations.MovieDetailsScreen(movieId = effect.mediaId.toInt())
                        )
                    } else if (effect.mediaType == MediaType.TV_SHOW) {
                        navController.navigate(
                            Destinations.SeriesDetailsScreen(
                                seriesId = effect.mediaId.toInt(),
                                seasonNumber = 1 // Default to season 1, can be changed later
                            )
                        )
                    }
                }
            }
        }
    }

    ViewAllScreenContent(
        state = state,
    )
}

@Composable
fun ViewAllScreenContent(
    state: ViewAllUiState,
) {

    LazyColumn {
        items(
            count = state.items.size,
            key = { index -> state.items[index].id }
        ) { index ->
            val item = state.items[index]
//            MovioText(
//                item.title,
//                textStyle = Theme.textStyle.body.mediumMedium12,
//            )
        }
    }
}