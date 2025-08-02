package com.madrid.presentation.screens.moreScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.presentation.R as presentationR
import com.madrid.designSystem.component.EmptySearchLayout
import com.madrid.designSystem.component.SettingsItem
import com.madrid.presentation.screens.moreScreen.component.ProfileSection
import com.madrid.presentation.viewModel.moreViewModel.MoreInteractionListener
import com.madrid.presentation.viewModel.moreViewModel.MoreUiState
import com.madrid.presentation.viewModel.moreViewModel.MoreViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoreScreen(
    viewModel: MoreViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    MoreScreenContent(
        state = state,
        interactionListener = viewModel as MoreInteractionListener
    )
}

@Composable
private fun MoreScreenContent(
    state: MoreUiState,
    interactionListener: MoreInteractionListener
) {
    if (state.isGuest) {
        EmptySearchLayout(
            title = "Log in to unlock your personal library",
            description = "Access your watch history, favorites, and watchlist  all in one place.",
            image = R.drawable.library_main_icon,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileSection(
                username = state.username,
                profilePicture = R.drawable.profile_pic_holder,
                onProfileClick = {  }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                SettingsItem(
                    icon = R.drawable.outline_star,
                    title = stringResource(presentationR.string.my_ratings),
                    clickable = true,
                    onClick = { interactionListener.onThemeClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_pallete2,
                    title =  stringResource(presentationR.string.theme),
                    text = if (state.isDarkModeEnabled) "Dark" else "Light",
                    clickable = true,
                    onClick = { interactionListener.onThemeClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_earth,
                    title = stringResource(presentationR.string.language),
                    text = state.language,
                    clickable = true,
                    onClick = { interactionListener.onThemeClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_smartphone,
                    title = stringResource(presentationR.string.app_version),
                    text = state.appVersion,
                    clickable = false,
                    onClick = { interactionListener.onThemeClick() }
                )
                SettingsItem(
                    icon = R.drawable.outline_arrows_logout,
                    title = stringResource(presentationR.string.logout),
                    clickable = true,
                    onClick = { interactionListener.onThemeClick() }
                )
            }
        }
    }
}