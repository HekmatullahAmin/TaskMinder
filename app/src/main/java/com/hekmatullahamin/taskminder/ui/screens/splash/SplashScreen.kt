package com.hekmatullahamin.taskminder.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.utils.Constants
import com.hekmatullahamin.taskminder.utils.Constants.SPLASH_SCREEN_TIMEOUT
import kotlinx.coroutines.delay

/**
 * Composable that represents the splash screen of the application.
 *
 * This screen is shown when the application starts, typically to display a loading
 * indicator or an error message while checking if the user's account is ready to be used.
 * It uses the `SplashViewModel` to manage the state of the app's readiness and triggers
 * the app startup process via the provided `onAppStart` callback when the app is ready.
 *
 * @param onAppStart A callback that will be invoked when the app is ready to start.
 * @param modifier A modifier that can be applied to the composable to control its appearance or layout.
 */
@Composable
fun SplashScreen(
    onAppStart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: SplashViewModel = hiltViewModel()
    val isAccountReady by viewModel.isAccountReady

    SplashScreenContent(
        modifier = modifier,
        onAppStart = viewModel::startTheApp,
        shouldShowError = viewModel.showError.value
    )

    LaunchedEffect(isAccountReady) {
        if (isAccountReady) {
            onAppStart()
//            viewModel.resetIsAccountReady()
        }
    }
}

/**
 * Composable that represents the content of the splash screen.
 *
 * Displays either a loading indicator or an error message based on the `shouldShowError` state.
 * The `onAppStart` callback is triggered when the user presses the "Try Again" button or when
 * the app is ready to start.
 *
 * @param modifier A modifier that can be applied to the composable to control its appearance or layout.
 * @param onAppStart A callback that will be invoked to start the application.
 * @param shouldShowError A flag indicating whether to show an error message or the loading indicator.
 */
@Composable
fun SplashScreenContent(
    modifier: Modifier = Modifier,
    onAppStart: () -> Unit,
    shouldShowError: Boolean
) {
    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (shouldShowError) {
            Text(text = stringResource(R.string.generic_error))
            Button(onClick = { onAppStart() }) {
                Text(text = stringResource(R.string.try_again))
            }
        } else {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onBackground)
        }
    }

    // Automatically start the app after the splash screen timeout
    LaunchedEffect(true) {
        delay(SPLASH_SCREEN_TIMEOUT)
        onAppStart()
    }
}