package com.madrid.presentation.screens.detailsScreen.detailsMovieScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.madrid.designSystem.R.drawable
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.component.TextWithReadMore
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.BottomMediaActions
import com.madrid.presentation.component.CastMember
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.component.header.MovieDetailsHeader
import com.madrid.presentation.component.movieActorBackground.MoviePosterDetailScreen
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.navigation.Destinations
import com.madrid.presentation.navigation.LocalNavController
import com.madrid.presentation.screens.detailsScreen.reviewsScreen.composables.ReviewScreen
import com.madrid.presentation.screens.detailsScreen.seriesDetails.toReviewScreenUiState
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMovie
import com.madrid.presentation.screens.detailsScreen.similarMedia.SimilarMoviesSection
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel

@Composable
fun MovieDetailsScreen(
    viewModel: DetailsMovieViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsState()
    val navController = LocalNavController.current
    val casts = uiState.casts
    var showAddRatingBottomSheet by remember { mutableStateOf(false) }

    if (uiState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptySearchLayout(
                title = stringResource(R.string.internet_is_not_available),
                description =
                    stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
                image = R.drawable.img_no_internet
            )
        }
    } else {

        Box(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(Theme.color.surfaces.surfaceContainer)
        ) {
            MoviePosterDetailScreen(
                imageUrl = uiState.topImageUrl,
                modifier = Modifier.fillMaxSize()
            )
            TopAppBar(
                text = null,
                modifier = Modifier.padding(start = 16.dp, top = 36.dp, end = 16.dp),
                onFirstIconClick = { navController.popBackStack() }
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 32.dp)
            ) {
                Spacer(modifier = Modifier.height(360.dp))
                MovieDetailsHeader(
                    movieName = uiState.movieName,
                    movieCategory = uiState.genreMovie,
                    date = uiState.dataMovie,
                    time = uiState.movieDuration,
                    rate = uiState.rate.take(3),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                BottomMediaActions(
                    onRateClick = {
                        showAddRatingBottomSheet = true
                    },
                    onPlayClick = {},
                    onAddToListClick = {},
                )

                MovioBottomSheet(
                    show = showAddRatingBottomSheet,
                    onDismiss = { showAddRatingBottomSheet = false },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            MovioArtistsCard(
                                imageUrl = uiState.topImageUrl,
                                circleImageSize = 88.dp,
                                artistsName = uiState.movieName,
                            )
                            MovioText(
                                modifier = Modifier.padding(top = 24.dp),
                                text = stringResource(R.string.add_your_overall_rating_for_this_movie),
                                color = Theme.color.surfaces.onSurfaceContainer,
                                textStyle = Theme.textStyle.label.smallRegular14
                            )
                            Row(
                                modifier = Modifier.padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                (1..5).forEach { i ->
                                    MovioIcon(
                                        painter = painterResource(drawable.bold_star),
                                        contentDescription = null,
                                        tint = if (i <= uiState.userRating) Theme.color.system.warning else Theme.color.surfaces.onSurfaceVariant,
                                        modifier = Modifier
                                            .size(28.dp)
                                            .clickable { viewModel.onPickRatingNumber(i) }
                                    )
                                }
                            }
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = 40.dp,
                                        bottom = 32.dp,
                                        start = 16.dp,
                                        end = 16.dp
                                    )
                                    .height(48.dp),
                                onClick = {
                                    viewModel.addRating()
                                    showAddRatingBottomSheet = false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Theme.color.brand.primary,
                                ),
                                shape = RoundedCornerShape(24.dp),
                                elevation = ButtonDefaults.elevation(0.dp)
                            ) {
                                MovioText(
                                    text = stringResource(R.string.submit),
                                    color = Theme.color.brand.onPrimary,
                                    textStyle = Theme.textStyle.label.mediumMedium14
                                )
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextWithReadMore(
                    description = uiState.description,
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    maxLines = 5
                )
                TopCastHorizontalScroll(
                    onSeeAllClick = {
                        navController.navigate(
                            Destinations.TopCast(
                                mediaId = uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    onCastMemberClick = { castId ->
                        navController.navigate(
                            Destinations.ActorDetails(
                                artistId = castId
                            )
                        )
                    },
                    modifier = Modifier.padding(top = 32.dp),
                    castMembers = uiState.casts.map { cast ->
                        CastMember(
                            id = cast.id.toString(),
                            name = cast.name,
                            imageUrl = cast.imageUrl
                        )
                    }
                )
                ReviewScreen(
                    modifier = Modifier.padding(top = 32.dp),
                    onSeeAllReviews = {
                        navController.navigate(
                            Destinations.ReviewsScreen(
                                uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    uiState = uiState.reviews.toReviewScreenUiState()
                )
                SimilarMoviesSection(
                    modifier = Modifier.padding(vertical = 32.dp),
                    onSeeAllClick = {
                        navController.navigate(
                            Destinations.SimilarMediaScreen(
                                mediaId = uiState.movieId,
                                isMovie = true
                            )
                        )
                    },
                    onMovieClick = { movie ->
                        navController.navigate(
                            Destinations.MovieDetailsScreen(
                                movieId = movie.id
                            )
                        )
                    },
                    similarMovies = uiState.similarMovies.map { movie ->
                        SimilarMovie(
                            id = movie.id,
                            title = movie.title,
                            imageUrl = movie.imageUrl,
                            rating = movie.rating
                        )
                    }
                )
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}