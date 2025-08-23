package com.madrid.presentation.screens.homeScreen.component

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.Chip

@Composable
fun HeaderHomeSectionBar(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
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
                isSelected = index == selectedTabIndex,
                title = title,
                onSelected = { onTabSelected(index) }
            )
        }
    }
}


@Preview
@Composable
private fun HeaderSectionBarPreview() {
    HeaderHomeSectionBar(
        tabs = listOf("Yasser", "Ahmed", "Messi", "Mohamed"),
        selectedTabIndex = 0,
        onTabSelected = {},
        modifier = Modifier
    )
}