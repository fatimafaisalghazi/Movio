package com.madrid.designSystem.component

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@Composable
fun ShimmerItem(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    contentAfterLoading: @Composable () -> Unit,
) {

    if(isLoading){
        Column(modifier = modifier.padding(top = 100.dp),horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier
                .size(width = 200.dp, height = 260.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect())
        }
    }else{
        var showContent by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(2000L)
            showContent = true
        }

        if(showContent.not()){
            Column(modifier = modifier.padding(top = 100.dp),horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier
                    .size(width = 200.dp, height = 260.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect())
            }
        }
        if(showContent)
            contentAfterLoading()
    }

}

@Composable
fun ShimmerHorizontalCard(
    isLoading: Boolean,
    primaryTextForCustomTextTitle: String,
    modifier: Modifier = Modifier,
    headerModifier: Modifier = Modifier,
    startIconForPrimaryTextTitle: Painter? = null,
    secondaryTextForCustomTextTitle: String? = null,
    endIconForCustomTextTitle: Painter? = null,
    onSeeAllClick: (() -> Unit)? = null,
    itemCount: Int = 5,
    contentAfterLoading: @Composable () -> Unit,
) {
    Column(modifier = modifier) {

        if (isLoading) {
            CustomTextTitle(
                modifier = headerModifier.padding(bottom = 12.dp),
                primaryText = primaryTextForCustomTextTitle,
                startIcon = startIconForPrimaryTextTitle,
                secondaryText = secondaryTextForCustomTextTitle,
                endIcon = endIconForCustomTextTitle,
                onSeeAllClick = onSeeAllClick,
                isListEmpty = isLoading
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(200.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(itemCount) {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(width = 124.dp, height = 160.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        } else {
            var showContent by remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                delay(2000L)
                showContent = true
            }
            if(!showContent){
                CustomTextTitle(
                    modifier = headerModifier.padding(bottom = 12.dp),
                    primaryText = primaryTextForCustomTextTitle,
                    startIcon = startIconForPrimaryTextTitle,
                    secondaryText = secondaryTextForCustomTextTitle,
                    endIcon = endIconForCustomTextTitle,
                    onSeeAllClick = onSeeAllClick,
                    isListEmpty = isLoading
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.height(200.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(itemCount) {
                        Column {
                            Box(
                                modifier = Modifier
                                    .size(width = 124.dp, height = 160.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .shimmerEffect()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(10.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .shimmerEffect()
                            )
                        }
                    }
                }
            }

            if (showContent) {
                contentAfterLoading()
            }
        }
    }
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(tween(1000)),
    )

    background(
        brush = Brush.linearGradient(colors = listOf(
            Color(0xFFB8B5B5),
            Color(0xFF8F8B8B),
            Color(0xFFB8B5B5),
        ),
            start = Offset(startOffsetX,0f),
            end = Offset(startOffsetX + size.width.toFloat(),size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}
