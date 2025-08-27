package com.madrid.designSystem.component

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.madrid.designSystem.R
import com.madrid.designSystem.theme.MovioTheme
import com.madrid.designSystem.theme.Theme

@Composable
fun TopAppBar(
    text: String?,
    modifier: Modifier = Modifier,
    startIcon: Int? = null,
    preEndIcon: Int? = null,
    endIcon: Int? = null,
    onStartIconClick: () -> Unit = {},
    onPreEndIconClick: () -> Unit = {},
    onEndIconClick: () -> Unit = {},
    isFavorite: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(1f)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        startIcon?.let { iconRes ->
            StartIcon(iconRes = iconRes, onFirstIconClick = onStartIconClick)
        } ?: Box(modifier = Modifier.size(24.dp))

        text?.let { TopAppBarTitle(modifier = Modifier.weight(1f), text = text) }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            preEndIcon?.let { iconRes ->
                PreEndIcon(iconRes = iconRes, onSecondIconClick = onPreEndIconClick)
            }
            endIcon?.let { iconRes ->
                EndIcon(isFavorite = isFavorite, onThirdIconClick = onEndIconClick)
            }
        }
    }
}

@Composable
private fun TopAppBarTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        MovioText(
            text = text,
            textStyle = Theme.textStyle.headline.largeBold18,
            color = Theme.color.surfaces.onSurface
        )
    }
}

@Composable
private fun StartIcon(
    iconRes: Int,
    onFirstIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovioIcon(
        painter = painterResource(id = iconRes),
        contentDescription = "back_button",
        tint = Theme.color.surfaces.onSurface,
        modifier = modifier.clickable { onFirstIconClick() }
    )
}

@Composable
private fun PreEndIcon(
    iconRes: Int,
    onSecondIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MovioIcon(
        painter = painterResource(id = iconRes),
        contentDescription = "share_button",
        tint = Theme.color.surfaces.onSurface,
        modifier = modifier.clickable { onSecondIconClick() }
    )

}

@Composable
private fun EndIcon(
    isFavorite: Boolean,
    onThirdIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
     val tintColor = animateColorAsState(
        targetValue =
        if (isFavorite)
            Theme.color.system.onErrorContainer
        else
            Theme.color.surfaces.onSurface,
         animationSpec = tween(durationMillis = 300)
    )
    MovioIcon(
        painter = painterResource(
            id = if (isFavorite)
                R.drawable.bold_heart
            else
                R.drawable.outline_heart
        ),
        contentDescription = "favorite_button",
        tint = tintColor.value,
        modifier = modifier.clickable(
            onClick = onThirdIconClick,
            interactionSource = MutableInteractionSource(),
            indication = null
        )
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TopAppBarPreview() {
    MovioTheme {
        TopAppBar(
            text = "Movie Name",
            startIcon = R.drawable.arrow_left,
            preEndIcon = R.drawable.share_arrow,
            endIcon = R.drawable.outline_heart
        )
    }
}