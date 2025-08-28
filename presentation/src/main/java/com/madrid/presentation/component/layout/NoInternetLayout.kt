package com.madrid.presentation.component.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R

@Composable
fun NoInternetLayout(onRetryButtonClick: () -> Unit , modifier:Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DialogWithButtonLayout(
            title = stringResource(R.string.internet_is_not_available),
            description = stringResource(R.string.please_make_sure_you_are_connected_to_the_internet_and_try_again),
            image = Theme.drawables.noInternetId,
            buttonText = stringResource(R.string.try_again),
            onClick = { onRetryButtonClick() },
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(16.dp)
        )
    }
}