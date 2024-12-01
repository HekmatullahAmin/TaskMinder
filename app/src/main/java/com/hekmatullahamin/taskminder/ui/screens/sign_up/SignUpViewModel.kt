package com.hekmatullahamin.taskminder.ui.screens.sign_up

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.ui.common.ext.isEmailValid
import com.hekmatullahamin.taskminder.ui.common.ext.isPasswordValid
import com.hekmatullahamin.taskminder.ui.common.ext.passwordMatches
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel responsible for managing the sign-up screen's UI state and business logic.
 *
 * The `SignUpViewModel` handles the logic of sign-up processes such as updating the email, password,
 * and repeat password fields, validating the input, and creating a new account. It also exposes the state
 * of the account creation process and allows resetting the success status once the account is created.
 *
 * @param logService The service responsible for logging throughout the application.
 * @param accountService The service responsible for linking accounts during the sign-up process.
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
) : TaskMinderViewModel(logService = logService) {

//    TODO: change it to StateFlow
    /**
     * The current UI state of the sign-up form.
     *
     * Holds the current values of the email, password, and repeat password fields.
     * This state is observed and updated as the user interacts with the form.
     */
    var uiState = mutableStateOf(SignUpUiState())
        private set

    /**
     * State indicating whether the account was successfully created.
     */
    private var _isAccountCreated: MutableState<Boolean> = mutableStateOf(false)
    val isAccountCreated: State<Boolean> get() = _isAccountCreated

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    /**
     * Updates the email field in the sign-up UI state.
     *
     * This function is called when the email input field changes.
     *
     * @param newValue The new value to set for the email field.
     */
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    /**
     * Updates the password field in the sign-up UI state.
     *
     * This function is called when the password input field changes.
     *
     * @param newValue The new value to set for the password field.
     */
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    /**
     * Updates the repeat password field in the sign-up UI state.
     *
     * This function is called when the repeat password input field changes.
     *
     * @param newValue The new value to set for the repeat password field.
     */
    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    /**
     * Attempts to create a new account with the current email and password.
     *
     * Validates the email, password, and repeat password fields before calling the
     * `accountService` to link the account. If any validation fails, an appropriate
     * error message is shown to the user via the `SnackbarManager`. If successful,
     * the `isAccountCreated` state is updated to `true`.
     */
    fun createAccount() {
        if (!email.isEmailValid()) {
            SnackbarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isPasswordValid()) {
            SnackbarManager.showMessage(R.string.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(R.string.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            _isAccountCreated.value = true
        }
    }

    /**
     * Resets the `isAccountCreated` state to `false`.
     *
     * This function is called when the sign-up process needs to be reset,
     * for instance, after handling the success of account creation.
     */
    fun resetIsAccountCreated() {
        _isAccountCreated.value = false
    }
}