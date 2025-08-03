package com.madrid.presentation.screens.moreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.component.TopAppBar
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.myRateViewModel.MyRateUiState
import com.madrid.presentation.viewModel.myRateViewModel.MyRateViewModel


@Composable
fun MyRatingScreen(
    viewModel: MyRateViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MyRatingScreenContent(
        state = state,
        onBackClick={}
    )
}

@Composable
private fun MyRatingScreenContent(state: MyRateUiState, onBackClick: () -> Unit) {
    Column(Modifier.padding( 16.dp)) {
        TopAppBar(
            text = stringResource(R.string.my_ratings),
            secondIcon = null,
            thirdIcon = null,
            onFirstIconClick = { onBackClick() },
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 101.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.color.surfaces.surface)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(
                span = { GridItemSpan(maxLineSpan) }
            ) {
                //TODO --> MovioRatingCard
            }
        }
    }
}