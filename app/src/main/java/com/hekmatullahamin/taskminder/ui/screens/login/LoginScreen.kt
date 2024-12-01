package com.hekmatullahamin.taskminder.ui.screens.login

import android.content.res.Configuration
import android.graphics.Bitmap.Config
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.ui.common.components.BasicToolbar
import com.hekmatullahamin.taskminder.ui.common.components.EmailField
import com.hekmatullahamin.taskminder.ui.common.components.PasswordField
import com.hekmatullahamin.taskminder.ui.common.ext.buttonModifier
import com.hekmatullahamin.taskminder.ui.common.ext.fieldModifier
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme
import kotlin.math.log

/**
 * Composable function that represents the Login Screen.
 *
 * This screen displays the email and password fields along with a sign-in button.
 * On successful sign-in, the `onSignInClick` callback is triggered.
 *
 * @param onSignInClick The callback to invoke when sign-in is successful.
 * This is usually used to navigate to another screen or perform other actions.
 * @param modifier A [Modifier] to customize the layout or appearance of the composable. Default is [Modifier].
 */
@Composable
fun LoginScreen(
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val loginViewModel: LoginViewModel = hiltViewModel()
    val uiState by loginViewModel.uiState
    val isSignInSuccess by loginViewModel.isSignInSuccess
    val onSignInToAccountMemoized: () -> Unit = remember { loginViewModel::signInToAccount }

    LaunchedEffect(isSignInSuccess) {
        if (isSignInSuccess) {
            onSignInClick()
            loginViewModel.resetIsSignInSuccess()
        }
    }

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = loginViewModel::onEmailChange,
        onPasswordChange = loginViewModel::onPasswordChange,
//        onSignInClick = loginViewModel::signInToAccount
        onSignInClick = onSignInToAccountMemoized
    )
}

/**
 * Composable function that represents the content of the Login Screen.
 *
 * This function includes the UI elements such as email and password fields,
 * and a sign-in button. The user can input their credentials and submit them
 * by clicking the sign-in button. The actual login logic is handled by the
 * [onSignInClick] callback, which is invoked when the button is clicked.
 *
 * @param uiState The current UI state that holds the email, password.
 * @param onEmailChange A lambda function that handles email input changes.
 * @param onPasswordChange A lambda function that handles password input changes.
 * @param onSignInClick A lambda function that handles the sign-in action.
 * @param modifier A [Modifier] to customize the layout or appearance of the composable. Default is [Modifier].
 */
@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            BasicToolbar(
                title = R.string.login_details,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            EmailField(
                value = uiState.email,
                onNewValue = onEmailChange,
                modifier = Modifier.fieldModifier()
            )
            PasswordField(
                value = uiState.password,
                onNewValue = onPasswordChange,
                placeholder = R.string.password,
                modifier = Modifier.fieldModifier()
            )
            Button(
                onClick = onSignInClick,
                modifier = Modifier.buttonModifier()
            ) {
                Text(text = stringResource(R.string.sign_in))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
private fun LoginScreenPreview() {
    TaskMinderTheme {
        LoginScreen(
            onSignInClick = {}
        )
    }
}