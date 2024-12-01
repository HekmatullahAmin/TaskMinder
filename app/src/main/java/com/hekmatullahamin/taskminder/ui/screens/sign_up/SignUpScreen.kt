package com.hekmatullahamin.taskminder.ui.screens.sign_up

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.ui.common.components.BasicToolbar
import com.hekmatullahamin.taskminder.ui.common.components.EmailField
import com.hekmatullahamin.taskminder.ui.common.components.PasswordField
import com.hekmatullahamin.taskminder.ui.common.ext.buttonModifier
import com.hekmatullahamin.taskminder.ui.common.ext.fieldModifier
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme

/**
 * Composable screen for user sign-up.
 *
 * This screen displays a form for the user to create a new account by entering their email,
 * password, and repeating the password. The `SignUpScreen` will handle the logic for account creation.
 * Upon successful account creation, a callback (`onSignUpClick`) is invoked.
 *
 * @param onSignUpClick A lambda function that will be invoked when the sign-up process is completed successfully.
 *                      This typically navigates the user to another screen.
 * @param modifier An optional modifier to be applied to the screen's layout.
 */
@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val uiState by signUpViewModel.uiState
    val isAccountCreated by signUpViewModel.isAccountCreated

    // LaunchedEffect to listen for account creation success and trigger the callback
    LaunchedEffect(isAccountCreated) {
        if (isAccountCreated) {
            onSignUpClick()
            signUpViewModel.resetIsAccountCreated()
        }
    }

    // Memoize the lambdas for stability
    val onSignUpClickMemoized: () -> Unit = remember { signUpViewModel::createAccount }

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = signUpViewModel::onEmailChange,
        onPasswordChange = signUpViewModel::onPasswordChange,
        onRepeatPasswordChange = signUpViewModel::onRepeatPasswordChange,
//        onSignUpClick = signUpViewModel::createAccount
        onSignUpClick = onSignUpClickMemoized
    )
}

/**
 * Composable content for the sign-up form, where the user can input their email, password, and repeat password.
 * This composable will display the fields and handle the user interaction for creating an account.
 *
 * @param uiState The current state of the UI containing the email, password, and repeat password fields.
 * @param onEmailChange A lambda function to handle the email input change.
 * @param onPasswordChange A lambda function to handle the password input change.
 * @param onRepeatPasswordChange A lambda function to handle the repeat password input change.
 * @param onSignUpClick A lambda function that is invoked when the user submits the sign-up form.
 * @param modifier An optional modifier to be applied to the layout of the sign-up form.
 */
@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val createAccountButtonContentDescription =
        stringResource(R.string.create_account_button_content_description)
    Scaffold(
        topBar = {
            BasicToolbar(
                title = R.string.create_account,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
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
            PasswordField(
                value = uiState.repeatPassword,
                onNewValue = onRepeatPasswordChange,
                placeholder = R.string.repeat_password,
                modifier = Modifier.fieldModifier()
            )
            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .buttonModifier()
                    .semantics {
                        contentDescription = createAccountButtonContentDescription
                    }
            ) {
                Text(text = stringResource(R.string.create_account))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenPreview() {
    TaskMinderTheme {
        SignUpScreen(
            onSignUpClick = {}
        )
    }
}