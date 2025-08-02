package com.madrid.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.designSystem.R
import com.madrid.designSystem.component.BottomSheet.AddToListBottomSheetContent
import com.madrid.designSystem.component.ButtomSheet.AuthRequiredBottomSheetContent
import com.madrid.designSystem.component.ButtomSheet.CreateListBottomSheet
import com.madrid.designSystem.component.ButtomSheet.RatingBottomSheetContent
import com.madrid.designSystem.component.ButtomSheet.ShareViaBottomSheetContent
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.MovioTheme

@Composable
fun BottomMediaActions(
    modifier: Modifier = Modifier,
    movieTitle: String = "",
    moviePosterResId: Int = 0,
    initialUserLists: List<Any> = emptyList(), // Replace with your actual UserList type
    onRateClick: ((Boolean) -> Unit)? = null,
    onPlayClick: (() -> Unit)? = null,
    onAddToListClick: ((Boolean) -> Unit)? = null,
    onRatingSubmitted: (Int) -> Unit = {},
    onListCreated: (String) -> Unit = {},
    onShareOptionClick: (Any) -> Unit = {}, // Replace with your actual ShareOption type
    onLoginRequested: () -> Unit = {}
) {
    var isRated by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }

    // Bottom Sheet States
    var showRatingBottomSheet by remember { mutableStateOf(false) }
    var showAddToListBottomSheet by remember { mutableStateOf(false) }
    var showCreateListBottomSheet by remember { mutableStateOf(false) }
    var showShareBottomSheet by remember { mutableStateOf(false) }
    var showAuthRequiredBottomSheet by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (onRateClick != null) {
            MediaActionItem(
                label = "rate",
                isActive = isRated,
                activeIcon = R.drawable.bold_star,
                inactiveIcon = R.drawable.outline_star,
                activeColor = Color.Yellow,
                onToggle = {
                    isRated = !isRated
                    onRateClick(isRated)
                    if (isRated) {
                        showRatingBottomSheet = true
                    }
                }
            )
        }
        if (onPlayClick != null) {
            PlayButton(onClick = onPlayClick)
        }

        if (onAddToListClick != null) {
            MediaActionItem(
                label = " add to list",
                isActive = isSaved,
                activeIcon = R.drawable.bold_bookmark,
                inactiveIcon = R.drawable.outline_bookmark,
                activeColor = Color(0xFF4CAF50),
                onToggle = {
                    isSaved = !isSaved
                    onAddToListClick(isSaved)
                    if (isSaved) {
                        showAddToListBottomSheet = true
                    }
                }
            )
        }
    }

    // Rating Bottom Sheet
    if (showRatingBottomSheet) {
        RatingBottomSheetContent(
            movieTitle = movieTitle,
            moviePosterResId = moviePosterResId,
            onRatingSubmitted = { rating ->
                onRatingSubmitted(rating)
                showRatingBottomSheet = false
            },
            onDismiss = { showRatingBottomSheet = false }
        )
    }
    // Add to List Bottom Sheet
    if (showAddToListBottomSheet) {
        AddToListBottomSheetContent(
            initialUserLists = initialUserLists,
            onListCreated = { showCreateListBottomSheet = true },
            onDismiss = { showAddToListBottomSheet = false },
            onSelectionChanged = { list, selected ->
                // Handle selection change
            }
        )
    }
    // Create List Bottom Sheet
    if (showCreateListBottomSheet) {
        CreateListBottomSheet(
            isVisible = true,
            onDismiss = { showCreateListBottomSheet = false },
            onCreateClick = { listName ->
                onListCreated(listName)
                showCreateListBottomSheet = false
                showAddToListBottomSheet = false
            }
        )
    }
    // Share Bottom Sheet
    if (showShareBottomSheet) {
        ShareViaBottomSheetContent(
            onDismiss = { showShareBottomSheet = false },
            onShareOptionClick = { option ->
                onShareOptionClick(option)
                showShareBottomSheet = false
            }
        )
    }
    // Auth Required Bottom Sheet
    if (showAuthRequiredBottomSheet) {
        AuthRequiredBottomSheetContent(
            title = "Authentication Required",
            description = "Please log in to rate movies or add them to your lists.",
            buttonText = "Login",
            onLoginClick = {
                onLoginRequested()
                showAuthRequiredBottomSheet = false
            },
            onDismiss = { showAuthRequiredBottomSheet = false }
        )
    }
}

@Composable
private fun MediaActionItem(
    label: String,
    isActive: Boolean,
    activeIcon: Int,
    inactiveIcon: Int,
    activeColor: Color,
    onToggle: () -> Unit
) {
    val animatedColor by animateColorAsState(
        targetValue = if (isActive) activeColor else Theme.color.surfaces.onSurfaceContainer,
        label = "MediaActionItemColorAnimation"
    )
    val icon = if (isActive) activeIcon else inactiveIcon
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MovioIcon(
            painter = painterResource(icon),
            contentDescription = label,
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    onClick = onToggle,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ),
            tint = animatedColor
        )
        MovioText(
            text = label,
            textStyle = Theme.textStyle.label.smallRegular12,
            color = Theme.color.surfaces.onSurfaceVariant,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xFFB7A4FB), Color(0xFF663EF6))
                ),
                shape = CircleShape
            )
            .clickable(
                onClick = onClick,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ),
        contentAlignment = Alignment.Center
    ) {
        MovioIcon(
            painter = painterResource(R.drawable.icon_paly),
            contentDescription = "Play",
            modifier = Modifier.size(28.dp),
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BottomActionBarPreview() {
    MovioTheme{
        BottomMediaActions(
            onRateClick = {},
            onPlayClick = {},
            onAddToListClick = {}
        )
    }
}