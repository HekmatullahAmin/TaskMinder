package com.hekmatullahamin.taskminder.ui.screens.login

import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.ui.common.ext.isEmailValid
import com.hekmatullahamin.taskminder.ui.common.ext.isPasswordValid
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for managing the UI state and business logic related to user login.
 *
 * This ViewModel holds the state for user email, password, and sign-in success. It provides functions
 * for updating the email and password fields, handling the sign-in logic, and resetting the sign-in
 * success state.
 *
 * The ViewModel communicates with the `AccountService` for authentication, and provides reactive
 * state updates for the login UI via `LoginUiState`.
 *
 * @property uiState The current state of the login screen UI, including the email and password values.
 * @property isSignInSuccess A boolean representing whether the user has successfully signed in.
 * @property onEmailChange A function to handle changes to the email field in the UI.
 * @property onPasswordChange A function to handle changes to the password field in the UI.
 * @property signInToAccount A function to perform authentication and update the sign-in status.
 * @property resetIsSignInSuccess A function to reset the sign-in success status to false.
 *
 * @param logService A service to log errors and information.
 * @param accountService A service to handle user authentication.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
) : TaskMinderViewModel(
    logService = logService
) {
    private val _uiState = mutableStateOf(LoginUiState())
    val uiState: State<LoginUiState> = _uiState

    private val email get() = _uiState.value.email
    private val password get() = _uiState.value.password

    // State for tracking sign-in success
    private val _isSignInSuccess = mutableStateOf(false)
    val isSignInSuccess get() = _isSignInSuccess

    /**
     * Updates the email field in the UI state when the user changes the email.
     *
     * @param email The new email entered by the user.
     */
    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    /**
     * Updates the password field in the UI state when the user changes the password.
     *
     * @param password The new password entered by the user.
     */
    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    /**
     * Performs the sign-in process, validating the email and password.
     * If successful, updates the sign-in success state.
     * If not, displays appropriate error messages.
     */
    fun signInToAccount() {
        if (!email.isEmailValid()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }
        if (password.isBlank()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            _isSignInSuccess.value = true
        }
    }

    /**
     * Resets the sign-in success state to false.
     */
    fun resetIsSignInSuccess() {
        _isSignInSuccess.value = false
    }
}