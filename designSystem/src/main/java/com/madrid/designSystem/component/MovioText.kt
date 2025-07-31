package com.madrid.designSystem.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

// Original MovioText composable that accepts a String
@Composable
fun MovioText(
    text: String,
    modifier: Modifier = Modifier,
    brush: Brush? = null,
    color: Color = Color.Unspecified,
    textStyle: TextStyle,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        text = text,
        textAlign = textAlign,
        style = if (brush != null) {
            textStyle.copy(brush = brush)
        } else {
            textStyle.copy(color = color)
        },
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow
    )
}

// Overloaded MovioText composable that accepts an AnnotatedString
@Composable
fun MoviosText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textStyle: TextStyle, // textStyle is a required parameter
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    // This version doesn't need to handle color/brush separately
    // because AnnotatedString handles spans and colors internally.
    Text(
        text = text,
        textAlign = textAlign,
        style = textStyle,
        modifier = modifier,
        maxLines = maxLines,
        overflow = overflow
    )
}