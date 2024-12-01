package com.hekmatullahamin.taskminder.ui.screens.account

/**
 * Represents the UI state of an account in the application.
 *
 * @property isAnonymousAccount A flag indicating whether the account is anonymous.
 * Anonymous accounts are typically used for users who haven't signed in or created an account.
 * Default is `true`, meaning the user starts as anonymous.
 */
data class AccountUiState(val isAnonymousAccount: Boolean = true)
