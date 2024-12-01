package com.hekmatullahamin.taskminder.ui.screens.account

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state and interactions related to the user's account.
 *
 * @param logService Service for logging events and errors.
 * @param accountService Service for handling account-related operations such as sign-in, sign-out, and account deletion.
 */
@HiltViewModel
class AccountViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
) : TaskMinderViewModel(logService) {

    /**
     * The current UI state of the account, reflecting whether the user is anonymous.
     */
    val uiState = accountService.currentUser.map {
        AccountUiState(isAnonymousAccount = it.isAnonymous)
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            AccountUiState()
        )

    /**
     * Flag indicating whether the user has signed out of their account.
     */
    private val _isAccountSignedOut = mutableStateOf(false)
    val isAccountSignedOut get() = _isAccountSignedOut

    /**
     * Flag indicating whether the user's account has been deleted.
     */
    private val _isAccountDeleted = mutableStateOf(false)
    val isAccountDeleted get() = _isAccountDeleted

    /**
     * Signs the user out of their account.
     * Updates the [_isAccountSignedOut] flag upon successful sign-out.
     */
    fun signOutFromAccount() {
        launchCatching {
            accountService.signOut()
            _isAccountSignedOut.value = true
        }
    }

    /**
     * Deletes the user's account.
     * Updates the [_isAccountDeleted] flag upon successful deletion.
     */
    fun deleteMyAccount() {
        launchCatching {
            accountService.deleteAccount()
            _isAccountDeleted.value = true
        }
    }

    /**
     * Resets the [_isAccountSignedOut] flag to `false`.
     * This is typically called after the sign-out process has been handled in the UI.
     */
    fun resetIsAccountSignedOut() {
        _isAccountSignedOut.value = false
    }

    /**
     * Resets the [_isAccountDeleted] flag to `false`.
     * This is typically called after the account deletion process has been handled in the UI.
     */
    fun resetIsAccountDeleted() {
        _isAccountDeleted.value = false
    }
}