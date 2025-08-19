package com.madrid.presentation.screens.detailsScreen.seriesDetails.component

import android.content.Context
import androidx.compose.runtime.Composable
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.ShareBottomSheetContent
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsInteractionListener
import com.madrid.presentation.viewModel.detailsViewModel.SeriesDetails.SeriesDetailsUiState

@Composable
fun ShareBottomSheet(
    copyToClipboard: (String) -> Unit,
    context: Context,
    uiState: SeriesDetailsUiState,
    interactionListener: SeriesDetailsInteractionListener
) {
    MovioBottomSheet(
        show = uiState.showSheet,
        onDismiss = { interactionListener.onDismissShareShareBottomSheetClick() },
        containerColor = Theme.color.surfaces.surface
    ) {
        ShareBottomSheetContent(
            onCopyLink = {
                copyToClipboard("https://www.themoviedb.org/tv/${uiState.seriesId}")
                interactionListener.onDismissShareShareBottomSheetClick()
            },
            onShareFacebook = {
                shareToApp(
                    "com.facebook.katana",
                    "https://www.themoviedb.org/tv/${uiState.seriesId}",
                    context = context
                )
                interactionListener.onDismissShareShareBottomSheetClick()
            },
            onShareX = {
                shareToApp(
                    "com.twitter.android",
                    "https://www.themoviedb.org/tv/${uiState.seriesId}",
                    context = context
                )
                interactionListener.onDismissShareShareBottomSheetClick()
            }
        )
    }
}