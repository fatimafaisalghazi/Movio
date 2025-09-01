package com.madrid.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.modifier.ShimmerCard

@Composable
fun ShimmerEffectCard(modifier: Modifier = Modifier) {
    ShimmerCard(
        isLoading = true,
        modifier = modifier
            .clip(RoundedCornerShape(size = 8.dp))
            .size(width = 150.dp, height = 180.dp)
    )
}