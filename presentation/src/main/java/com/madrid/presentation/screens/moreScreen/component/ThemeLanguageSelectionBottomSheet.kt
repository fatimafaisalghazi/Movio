package com.madrid.presentation.screens.moreScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.R
import com.madrid.designSystem.component.MovioBottomSheet
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioIcon
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.viewModel.moreViewModel.LanguageType
import com.madrid.presentation.viewModel.moreViewModel.MoreInteractionListener
import com.madrid.presentation.viewModel.moreViewModel.MoreUiState
import com.madrid.presentation.viewModel.moreViewModel.SettingType
import com.madrid.presentation.viewModel.moreViewModel.ThemeType

@Composable
fun ThemeLanguageSelectionBottomSheet(
    state: MoreUiState,
    interaction: MoreInteractionListener,
    title: String = "",
    settingType: SettingType
) {
    MovioBottomSheet(
        show = when (settingType) {
            SettingType.THEME -> state.isThemeSheetVisible
            SettingType.LANGUAGE -> state.isLanguageSheetVisible
        },
        onDismiss = interaction::onDismissBottomSheet,
        containerColor = Theme.color.surfaces.surface,
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Title(
                    title = title,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                val currentTheme = state.selectedTheme
                val currentLanguage = state.selectedLanguage
                when (settingType) {
                    SettingType.THEME -> {
                        Option(
                            option = stringResource(R.string.dark_mode),
                            leadingIcon = painterResource(R.drawable.outline_moon_stars),
                            isSelected = currentTheme == ThemeType.DARK,
                            onClick = { interaction.onConfirmTheme(ThemeType.DARK) }
                        )
                        Option(
                            option = stringResource(R.string.light_mode),
                            leadingIcon = painterResource(R.drawable.outline_sun),
                            isSelected = currentTheme == ThemeType.LIGHT,
                            onClick = { interaction.onConfirmTheme(ThemeType.LIGHT) }
                        )
                    }

                    SettingType.LANGUAGE -> {
                        Option(
                            option = stringResource(R.string.english),
                            onClick = { interaction.onConfirmLanguage(LanguageType.ENGLISH) },
                            isSelected = currentLanguage == LanguageType.ENGLISH
                        )
                        Option(
                            option = stringResource(R.string.arabic),
                            onClick = { interaction.onConfirmLanguage(LanguageType.ARABIC) },
                            isSelected = currentLanguage == LanguageType.ARABIC
                        )
                    }
                }
            }
            item {
                MovioButton(
                    color = Theme.color.brand.primary,
                    onClick = interaction::onDismissBottomSheet,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    MovioText(
                        text = "Confirm",
                        color = Theme.color.brand.onPrimary,
                        textStyle = Theme.textStyle.label.mediumMedium14,
                    )
                }
            }
        }
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    title: String,
) {
    MovioText(
        modifier = modifier,
        text = title,
        color = Theme.color.surfaces.onSurface,
        textStyle = Theme.textStyle.title.mediumMedium14,
    )
}

@Composable
private fun Option(
    option: String,
    modifier: Modifier = Modifier,
    leadingIcon: Painter? = null,
    trailingIcon: Painter = painterResource(R.drawable.bold_check_circle),
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColors = listOf(
        Color(0xff724CF8),
        Theme.color.brand.primary
    )
    val shape = RoundedCornerShape(8.dp)
    Row(
        modifier = modifier
            .padding(vertical = 12.dp)
            .clip(shape)
            .then(
                if (isSelected)
                    Modifier.border(
                        brush = Brush.linearGradient(borderColors),
                        width = 1.dp,
                        shape = shape
                    )
                else
                    Modifier
            )
            .background(Theme.color.surfaces.surfaceContainer)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leadingIcon != null) {
            MovioIcon(
                modifier = Modifier.padding(end = 8.dp),
                painter = leadingIcon,
                contentDescription = null,
                tint = Theme.color.surfaces.onSurface
            )
        }
        MovioText(
            modifier = Modifier.weight(1f),
            text = option,
            color = Theme.color.surfaces.onSurface,
            textStyle = Theme.textStyle.title.mediumMedium14,
        )
        if (isSelected) {
            MovioIcon(
                modifier = Modifier.padding(end = 8.dp),
                painter = trailingIcon,
                contentDescription = null,
                tint = Theme.color.brand.primary
            )
        }
    }
}