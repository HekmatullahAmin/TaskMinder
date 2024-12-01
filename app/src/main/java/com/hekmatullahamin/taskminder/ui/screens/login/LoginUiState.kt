package com.hekmatullahamin.taskminder.ui.screens.login

import android.provider.ContactsContract.CommonDataKinds.Email

/**
 * A data class representing the UI state for the login screen.
 *
 * This class holds the state values for the user's email and password
 * inputs on the login screen.
 *
 * @property email The email entered by the user.
 * @property password The password entered by the user.
 *
 * @constructor Creates a [LoginUiState] with optional initial values for email and password.
 * Defaults to empty strings for both.
 */
data class LoginUiState(
    val email: String = "",
    val password: String = "",
)
