package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.runtime.Composable
import com.madrid.presentation.component.TopCastHorizontalScroll
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState

@Composable
fun TopCastSection(
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener
) {
    val artists = uiState.topCast

    TopCastHorizontalScroll(
        castMembers = artists,
        onSeeAllClick = {
            interactionListener.onSeeAllClick(uiState.seriesId, seeAllType = SeeAllType.TopCast)
        },
        onCastMemberClick = { castId ->
            interactionListener.onActorCardClick(uiState.seriesId)
        }
    )
}