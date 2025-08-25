package com.madrid.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.madrid.designSystem.R
import com.madrid.designSystem.modifier.shimmerEffect
import com.madrid.designSystem.theme.LocalIsDarkTheme


@Composable
fun ImageViewer(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    loading: (@Composable () -> Unit)? = { LoadingShimmerEffect() },
    success: (@Composable () -> Unit)? = null,
    error: (@Composable () -> Unit)? = { ErrorImagePlaceHolder() },
    onLoading: (() -> Unit)? = null,
    onSuccess: (() -> Unit)? = null,
    onError: () -> Unit = {},
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        loading = loading?.let { { it() } },
        success = success?.let { { it() } },
        error = error?.let { { it() } },
        onLoading = onLoading?.let { { it() } },
        onSuccess = onSuccess?.let { { it() } },
        onError = { onError() },
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
    )
}


@Composable
private fun LoadingShimmerEffect(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(8.dp))
            .shimmerEffect(),
    )
}

@Composable
fun ErrorImagePlaceHolder(modifier: Modifier = Modifier) {
    val isDarkTheme = LocalIsDarkTheme.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color(0xff252E3C) else Color(0xffA1AEC3)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(0.3f),
            imageVector = ImageVector.vectorResource(id = R.drawable.placeholder_icon),
            contentDescription = stringResource(R.string.image_loading_error),
            colorFilter = ColorFilter.tint(Color(0xFFEFF1F5))
        )
    }
}

@Preview
@Composable
private fun ImageViewerPreview() {
    ImageViewer(
        model = "https://picsum.photos/200",
        contentDescription = "Image",
    )
}
