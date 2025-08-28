package com.madrid.presentation.screens.detailsScreen.similarMedia

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.CustomTextTitle
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.component.movioCards.MovioVerticalCard
import com.madrid.presentation.viewModel.detailsViewModel.SeeAllType
import com.madrid.presentation.viewModel.detailsViewModel.SeriesUiState


@Composable
fun SimilarSeriesSection(
    similarSeries: List<SeriesUiState>,
    modifier: Modifier = Modifier,
    onSeeAllClick: () -> Unit = {},
    onSeriesClick: (SeriesUiState) -> Unit = {}
) {
    Column(modifier = modifier) {
        CustomTextTitle(
            primaryText = stringResource(SeeAllType.SimilarSeries.stringResId),
            secondaryText = stringResource(R.string.see_all),
            endIcon = painterResource(com.madrid.designSystem.R.drawable.outline_alt_arrow_left),
            onSeeAllClick = { onSeeAllClick() },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(similarSeries) { series ->
                SeriesCard(series = series, onClick = { onSeriesClick(series) })
            }
        }
    }
}

@Composable
private fun SeriesCard(
    series: SeriesUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(width = 124.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier
                .height(height = 200.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(size = 8.dp))
        ) {
            MovioVerticalCard(
                description = series.name,
                movieImage = series.imageUrl,
                rate = series.rate,
                width = 124.dp,
                imageHeight = 160.dp,
                onClick = onClick,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun SimilarMoviesSectionPreview() {
    val fakeMovies = listOf(
        SeriesUiState(
            id = 1,
            name = "Spider-Man: Into the Spider-Verse",
            imageUrl = "https://image.tmdb.org/t/p/w500/iiZZdoQBEYBv6id8su7ImL0oCbD.jpg",
            rate = 4.8.toString()
        ),
        SeriesUiState(
            id = 2,
            name = "The Dark Knight",
            imageUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
            rate = 5.0.toString()
        ),
        SeriesUiState(
            id = 3,
            name = "Grave of the Fireflies",
            imageUrl = "https://image.tmdb.org/t/p/w500/qG3RYlIVpTYclR9TYIsy8p7m7AT.jpg",
            rate = 4.7.toString()
        )
    )
    MovioTheme {
        Box(
            modifier = Modifier.background(Theme.color.surfaces.surfaceContainer)
        ) {
            SimilarSeriesSection(similarSeries = fakeMovies)
        }
    }
}