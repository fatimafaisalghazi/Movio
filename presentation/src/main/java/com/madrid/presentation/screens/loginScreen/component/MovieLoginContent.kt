import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.madrid.designSystem.theme.Theme
import com.madrid.presentation.R
import com.madrid.presentation.screens.loginScreen.component.AnimatedLoginButton
import com.madrid.presentation.screens.loginScreen.component.LoginErrorAndForgotPassword
import com.madrid.presentation.screens.loginScreen.component.LoginHeader
import com.madrid.presentation.screens.loginScreen.component.LoginInputFields
import com.madrid.presentation.screens.loginScreen.component.OrDivider
import com.madrid.presentation.screens.loginScreen.component.SignUpRow
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
            .background(Theme.color.surfaces.surface)
            .padding(horizontal = 16.dp, vertical = 32.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Spacer(modifier = Modifier.height(74.dp))
            LoginHeader()
            Spacer(modifier = Modifier.height(48.dp))
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
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedLoginButton(
                isLoading = state.isGuestLoading,
                onClick = onGuestLogin,
                buttonColor = Theme.color.surfaces.onSurfaceAt3.copy(alpha = 0.12f),
                textColor = Theme.color.surfaces.onSurface,
                text = stringResource(R.string.continue_as_a_guest),
            )
        }
        SignUpRow(onSignUpClick = onSignUpClick)
    }
}