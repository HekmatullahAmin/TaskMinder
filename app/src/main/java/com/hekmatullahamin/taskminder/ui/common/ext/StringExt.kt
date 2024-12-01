package com.hekmatullahamin.taskminder.ui.common.ext

import android.util.Patterns
import java.util.regex.Pattern

private const val MIN_PASS_LENGTH = 6
private const val PASS_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$"

/**
 * Checks if the email string is valid.
 * A valid email should not be blank and should match the email pattern.
 *
 * @return true if the email is valid, false otherwise.
 */
fun String.isEmailValid() = this.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Checks if the password string is valid.
 * A valid password should be at least [MIN_PASS_LENGTH] characters long
 * and should match the pattern defined by [PASS_PATTERN], which requires:
 * - At least one digit.
 * - At least one lowercase letter.
 * - At least one uppercase letter.
 * - No whitespace.
 * - At least [MIN_PASS_LENGTH] characters in length.
 *
 * @return true if the password is valid, false otherwise.
 */
fun String.isPasswordValid(): Boolean {
    return this.length >= MIN_PASS_LENGTH &&
            Pattern.compile(PASS_PATTERN).matcher(this).matches()
}

/**
 * Checks if the current password matches the repeated password.
 *
 * @param repeatedPassword The repeated password to compare with.
 * @return true if both passwords match, false otherwise.
 */
fun String.passwordMatches(repeatedPassword: String): Boolean {
    return this == repeatedPassword
}

/**
 * Formats a day of the month into a 2-digit string.
 * Example: "1" becomes "01", "10" stays "10".
 *
 * @return The formatted day as a 2-digit string.
 */
fun String.formatDay(): String = this.padStart(2, '0')