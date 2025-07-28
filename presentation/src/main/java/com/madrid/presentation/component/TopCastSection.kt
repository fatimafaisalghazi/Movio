package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.madrid.designSystem.R
import com.madrid.designSystem.component.CustomTextTitel
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.presentation.component.movioCards.MovioArtistsCard
import com.madrid.presentation.viewModel.detailsViewModel.DetailsMovieViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun TopCastSection(
    modifier: Modifier = Modifier,
    castMembers: DetailsMovieViewModel = koinViewModel(),
    onSeeAllClick: () -> Unit ={},
    onCastMemberClick:(Int) ->Unit={}
) {
    val uiState  by castMembers.state.collectAsStateWithLifecycle()
    val cast = uiState.casts
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        CustomTextTitel(
            primaryText = stringResource(com.madrid.presentation.R.string.top_cast),
            secondaryText = stringResource(com.madrid.presentation.R.string.see_all),
            endIcon = painterResource(R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onSeeAllClick() }
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cast.size) {index ->
                MovioArtistsCard(
                    imageUrl = cast[index].imageUrl,
                    artistsName = cast[index].name,
                    onClick = { onCastMemberClick(cast[index].id.toInt())}
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TopCastSectionPreview() {
    MovioTheme {
        TopCastSection()
    }
} 