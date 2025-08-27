package com.madrid.presentation.screens.searchScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.chip.Chip
import com.madrid.presentation.screens.searchScreen.utils.SearchSections

@Composable
fun HeaderSearchSectionBar(
    tabs: List<String>,
    selectedTabIndex: SearchSections,
    onTabSelected: (SearchSections) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, title ->
            Chip(
                isSelected = selectedTabIndex == SearchSections.entries[index],
                title = title,
                onSelected = { onTabSelected(SearchSections.entries[index]) }
            )
        }
    }
}