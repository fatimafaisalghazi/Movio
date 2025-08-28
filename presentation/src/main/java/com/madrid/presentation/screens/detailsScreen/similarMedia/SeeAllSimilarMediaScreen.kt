package com.madrid.presentation.screens.detailsScreen.similarMedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.R
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.MediaUiState
import com.madrid.presentation.viewModel.detailsViewModel.similarMedia.SimilarMediaViewModel

@Composable
fun SeeAllSimilarMediaScreen(
    viewModel: SimilarMediaViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val mediaUiState = uiState.medias

    SeeAllSimilarMediaScreenContent(
        similarMovies = mediaUiState,
        headerName = uiState.headerName,
        isMovie = uiState.isMovie,
        onClickBack = { navController.popBackStack() },
        onClickMedia = { id, isMovie ->
            if (isMovie) {
                navController.navigate(route = Destinations.MovieDetailsScreen(movieId = id))
            } else {
                navController.navigate(route = Destinations.SeriesDetailsScreen(seriesId = id, seasonNumber = 1))
            }
        }
    )
}

@Composable
fun SeeAllSimilarMediaScreenContent(
    similarMovies: List<MediaUiState>,
    headerName: String,
    isMovie: Boolean,
    onClickBack: () -> Unit = {},
    onClickMedia: (Int, Boolean) -> Unit = { _, _ -> }
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            text = headerName,
            startIcon = R.drawable.arrow_left,
            modifier = Modifier.padding(start = 16.dp, top = 32.dp, end = 16.dp),
            onStartIconClick = { onClickBack() }
        )

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 101.33.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Theme.color.surfaces.surface)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp)
        ) {
            items(count = similarMovies.size) { index ->
                val movie = similarMovies[index]
                MovioVerticalCard(
                    description = movie.mediaName,
                    movieImage = movie.imageUrl,
                    rate = movie.rate,
                    width = 101.dp,
                    imageHeight = 160.dp,
                    onClick = { onClickMedia(movie.mediaId, isMovie) },
                )
            }
        }
    }
}