package com.madrid.presentation.screens.detailsScreen.similarMedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.madrid.designSystem.R
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.layout.DialogWithButtonLayout
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations.MovieDetailsScreen
import com.madrid.presentation.navigation.Destinations.SeriesDetailsScreen
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.castDetails.LoadingScreen
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.SimilarMediaEffect
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.SimilarMediaInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.SimilarMediaUiState
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.SimilarMediaViewModel
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SeeAllSimilarMediaScreen(
    viewModel: SimilarMediaViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    HandelNavigation(navController = navController, uiState = uiState, effect = viewModel.effect)

    SeeAllSimilarMediaScreenContent(
        uiState = uiState,
        headerName = uiState.headerName,
        listener = viewModel
    )
}

@Composable
private fun HandelNavigation(
    navController: NavController,
    uiState: SimilarMediaUiState,
    effect: SharedFlow<SimilarMediaEffect>
) {
    LaunchedEffect(effect) {
        effect.collect { effect ->
            when (effect) {
                is SimilarMediaEffect.NavigateToDetails ->
                    if (uiState.isMovie) {
                        navController.navigate(route = MovieDetailsScreen(movieId = effect.id))
                    } else {
                        navController.navigate(route = SeriesDetailsScreen(seriesId = effect.id, seasonNumber = 1))
                    }
                is SimilarMediaEffect.NavigateBacK -> navController.popBackStack()
            }
        }
    }
}

@Composable
fun SeeAllSimilarMediaScreenContent(
    headerName: String,
    uiState: SimilarMediaUiState,
    listener: SimilarMediaInteractionListener
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            text = headerName,
            startIcon = R.drawable.arrow_left,
            modifier = Modifier.statusBarsPadding(),
            onStartIconClick = listener::onBackClick
        )

        when {
            uiState.showLoadingScreen -> {
                LoadingScreen(message = stringResource(id = com.madrid.presentation.R.string.loading))
            }

            uiState.isError -> {

                DialogWithButtonLayout(
                    title = stringResource(id = com.madrid.presentation.R.string.internet_is_not_available),
                    description = stringResource(id = com.madrid.presentation.R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                    image = Theme.drawables.noInternetId,
                    buttonText = stringResource(id = com.madrid.presentation.R.string.try_again),
                    onClick = { listener::onRetryButtonClick },
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .padding(16.dp)
                )
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 101.33.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Theme.color.surfaces.surface),
                    contentPadding = PaddingValues(vertical = 6.dp),
                    horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 6.dp)
                ) {
                    items(items = uiState.medias) { media ->
                        MovioVerticalCard(
                            description = media.mediaName,
                            movieImage = media.imageUrl,
                            rate = media.rate,
                            width = 101.dp,
                            imageHeight = 160.dp,
                            onClick = { listener::onMediaCardClick.invoke(media.mediaId) }
                        )
                    }
                }
            }
        }
    }
}