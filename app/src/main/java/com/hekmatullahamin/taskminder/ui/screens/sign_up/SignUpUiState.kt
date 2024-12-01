package com.hekmatullahamin.taskminder.ui.screens.sign_up

/**
 * Represents the UI state for the sign-up screen.
 *
 * This data class holds the values of the fields in the sign-up form, such as email, password,
 * and repeat password. It is used to manage and observe the state of the input fields in the UI.
 *
 * @property email The email entered by the user. Initially, it's an empty string.
 * @property password The password entered by the user. Initially, it's an empty string.
 * @property repeatPassword The password entered by the user for confirmation. Initially, it's an empty string.
 */
data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
