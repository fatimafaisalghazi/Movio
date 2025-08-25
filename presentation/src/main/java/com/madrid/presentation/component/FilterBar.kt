package com.madrid.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.chip.FilterChip

@Composable
fun FilterBar(
    tabs: List<String>,
    selectedItem: String,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentHorizontalPadding: Dp = 16.dp,
    rowState: LazyListState = rememberLazyListState()
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = contentHorizontalPadding),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        state = rowState
    ) {
        itemsIndexed(tabs) { index, item ->
            FilterChip(
                text = item,
                isSelected = item == selectedItem,
                onClick = { onItemClick(item) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FilterBarPreview() {
    val items =
        listOf("Movies", "Series", "Artists", "Top Rated", "Top Rated", "Top Rated", "Top Rated")
    val selected = remember { mutableStateOf("Movies") }

    FilterBar(
        tabs = items,
        selectedItem = selected.value,
        onItemClick = { selected.value = it },
    )
}