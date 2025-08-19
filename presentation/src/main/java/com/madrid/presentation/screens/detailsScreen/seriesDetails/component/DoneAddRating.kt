package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState

@Composable
fun DoneAddRating(
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener
) {
    MovioBottomSheet(
        show = uiState.showDoneRatingBottomSheet,
        onDismiss = { interactionListener.onDismissShowDoneRatingBottomSheetClick() },
        content = {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.party_icon),
                    contentDescription = "Party Icon",
                    modifier = Modifier
                        .size(68.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp),
                )
                MovioText(
                    text = stringResource(R.string.thank_you_for_your_rating),
                    textStyle = Theme.textStyle.label.smallRegular14,
                    color = Theme.color.surfaces.onSurfaceContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    (1..5).forEach { i ->
                        MovioIcon(
                            painter = painterResource(com.madrid.designSystem.R.drawable.bold_star),
                            contentDescription = null,
                            tint = if (i <= uiState.userRating) Theme.color.system.warning else Theme.color.surfaces.onSurfaceVariant,
                            modifier = Modifier
                                .size(if (i == uiState.userRating) 48.dp else 28.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
                        .height(48.dp),
                    onClick = { interactionListener.onDismissShareShareBottomSheetClick() },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Theme.color.brand.primary,
                    ),
                    shape = RoundedCornerShape(24.dp),
                    elevation = ButtonDefaults.elevation(0.dp)
                ) {
                    MovioText(
                        text = stringResource(R.string.done),
                        color = Theme.color.brand.onPrimary,
                        textStyle = Theme.textStyle.label.mediumMedium14
                    )
                }
            }
        }
    )
}