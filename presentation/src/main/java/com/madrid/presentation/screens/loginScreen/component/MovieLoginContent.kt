package com.madrid.presentation.screens.loginScreen.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.component.MovioButton
import com.madrid.designSystem.component.MovioText
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.viewModel.loginViewModel.LoginUiState


@Composable
fun MovieLoginContent(
    state: LoginUiState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onTogglePassword: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGuestLogin: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background( Theme.color.surfaces.surface)

            .padding(horizontal = 16.dp, vertical = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(74.dp))
        LoginHeader()
    
        LoginInputFields(
            state = state,
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange,
            onTogglePassword = onTogglePassword,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        LoginErrorAndForgotPassword(
            state = state,
            onForgotPasswordClick = onForgotPasswordClick,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        AnimatedLoginButton(
            isLoading = state.isLoading,
            onClick = onLoginClick,
            text = stringResource(R.string.login),

        )

        Spacer(modifier = Modifier.height(40.dp))

        OrDivider()
        AnimatedLoginButton(
            isLoading = state.isGuest,
            onClick = onGuestLogin,
            buttonColor = Theme.color.surfaces.onSurfaceAt3,
            textColor = Theme.color.surfaces.onSurface,
            text = stringResource(R.string.continue_as_a_guest),

            )


        Spacer(modifier = Modifier.weight(1f))

        SignUpRow(onSignUpClick = onSignUpClick,)
    }
}
