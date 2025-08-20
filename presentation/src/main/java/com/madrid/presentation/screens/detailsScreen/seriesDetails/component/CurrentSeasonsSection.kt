package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioSeasonCard
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState
import com.madrid.presentation.viewModel.shared.parser.formatFullDateKtx
import com.madrid.presentation.viewModel.shared.parser.formatYearKtx

@Composable
fun CurrentSeasonsSection(
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener
) {
    val seasons = uiState.currentSeasonsUiStates

    CurrentSeasonsTitle(
        uiState = uiState, interactionListener=interactionListener
    )
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(seasons) { _, season ->
            MovioSeasonCard(
                movieTitle = season.title,
                movieImage = season.imageUrl,
                movieRate = season.rate,
                totalNumberOfEpisodes = season.numberOfEpisodes.toString(),
                onClick = {
                    interactionListener.onCurrentSeasonCardClick(
                        uiState.seriesId,
                        season.seasonNumber
                    )
                },
                yearOfPublish = season.productionDate.formatYearKtx(),
                currentSeason = season.seasonNumber.toString(),
                timeOfPublish = season.productionDate.formatFullDateKtx(),
                modifier = Modifier.width(250.dp)
            )
        }
    }
}

@Composable
fun CurrentSeasonsTitle(uiState: SeriesDetailsUiState,interactionListener:SeriesDetailsInteractionListener) {
    CustomTextTitle(
        primaryText = stringResource(SeeAllType.Season.stringResId),
        secondaryText = stringResource(R.string.see_all),
        endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
        onSeeAllClick = { interactionListener.onSeeAllClick(uiState.seriesId,SeeAllType.Season) },
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 12.dp)
    )
}
