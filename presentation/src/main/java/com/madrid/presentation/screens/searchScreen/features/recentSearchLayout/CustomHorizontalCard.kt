package com.madrid.presentation.screens.searchScreen.features.recentSearchLayout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.shared.MediaUiState

@Composable
fun CustomHorizontalCard(
    primaryTextForCustomTextTitel: String,
    listOfMedia: List<MediaUiState>,
    modifier: Modifier = Modifier,
    secondaryTextForCustomTextTitel: String? = null,
    endIconForCustomTextTitel: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    onMediaClick: (MediaUiState) -> Unit = {},
) {
    Column(modifier = modifier) {
        CustomTextTitel(
            primaryText = primaryTextForCustomTextTitel,
            secondaryText = secondaryTextForCustomTextTitel,
            endIcon = endIconForCustomTextTitel,
            onSeeAllClick = onSeeAllClick
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(listOfMedia) { media ->
                MovioVerticalCard(
                    description = media.title,
                    movieImage = media.imageUrl,
                    rate = media.rating,
                    width = 124.dp,
                    height = 160.dp,
                    paddingValue = 8.dp,
                    onClick = { onMediaClick(media) }
                )
            }
        }
    }
}