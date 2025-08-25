package com.madrid.designSystem.component.chip

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme

@Composable
fun Chip(
    isSelected: Boolean,
    title: String,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    var tabWidth by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current
    val textColor by animateColorAsState(
        targetValue = if (isSelected)
            Theme.color.brand.onPrimaryContainer
        else
            Theme.color.surfaces.onSurfaceVariant,
        animationSpec = tween(durationMillis = 300),
        label = "tabTextColor"
    )

    val textStyle = if (isSelected)
        Theme.textStyle.title.mediumMedium16
    else
        Theme.textStyle.body.smallRegular16

    val underlineAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0f,
        animationSpec = tween(durationMillis = 300),
        label = "underlineAlpha"
    )

    Column(
        modifier = modifier
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onSelected() }
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        MovioText(
            modifier = Modifier
                .widthIn(min = 48.dp)
                .onGloballyPositioned { coordinates -> tabWidth = coordinates.size.width },
            text = title,
            color = textColor,
            textStyle = textStyle,
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .height(height = 1.dp)
                .width(width = with(receiver = density) { tabWidth.toDp() })
                .alpha(underlineAlpha)
                .background(brush = Theme.color.gradients.underlineGlowBrushGradient)
        )
    }
}

@Preview
@Composable
private fun ChipPreview() {
    Chip(
        isSelected = true,
        title = "All",
        onSelected = {}
    )
}