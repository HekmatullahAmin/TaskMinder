package com.hekmatullahamin.taskminder.ui.screens.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.auth.FirebaseAuthException
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.ConfigurationService
import com.hekmatullahamin.taskminder.data.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for handling the logic of the SplashScreen.
 *
 * This ViewModel is responsible for initializing the application by checking if the user
 * is signed in. If not, it creates an anonymous account and triggers the start of the app.
 * It also fetches the application configuration at initialization and manages the error state
 * if an issue arises during account creation.
 *
 * @param accountService Service responsible for managing user accounts.
 * @param configurationService Service that fetches the app's configuration.
 * @param logService Service used for logging and error tracking.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService,
    configurationService: ConfigurationService,
    logService: LogService
) : TaskMinderViewModel(logService) {

    /**
     * A state indicating if the account is ready for use.
     * Once the user is signed in or an anonymous account is created, this value will be set to true.
     */
    private var _isAccountReady = mutableStateOf(false)
    val isAccountReady: State<Boolean>
        get() = _isAccountReady

    /**
     * A state indicating whether an error occurred during the app startup process, such as
     * failure to create an anonymous account.
     */
    var showError: MutableState<Boolean> = mutableStateOf(false)
        private set

    /**
     * Initializes the ViewModel by fetching the app configuration.
     */
    init {
        launchCatching {
            configurationService.fetchConfiguration()
        }
    }

//    TODO: check if you need the below function
    /**
     * Starts the application by checking if the user is signed in.
     * If the user is signed in, it sets the `isAccountReady` state to true.
     * If the user is not signed in, it creates an anonymous account and proceeds to set
     * `isAccountReady` to true upon success.
     */
    fun startTheApp() {
        showError.value = false
        if (accountService.isUserSignedIn) {
            _isAccountReady.value = true
        } else {
            createAnonymousAccount()
        }
    }

    /**
     * Creates an anonymous account if the user is not signed in.
     * This function will set the `isAccountReady` state to true once the account is created
     * successfully or show an error if account creation fails.
     *
     * @throws FirebaseAuthException If there is an error creating the anonymous account.
     */
    private fun createAnonymousAccount() {
        launchCatching(snackbar = false) {
            try {
                accountService.createAnonymousAccount()
            } catch (e: FirebaseAuthException) {
                showError.value = true
                throw e
            }
            _isAccountReady.value = true
        }
    }

    /**
     * Resets the `isAccountReady` state.
     */
//    fun resetIsAccountReady() {
//        _isAccountReady.value = false
//    }
}