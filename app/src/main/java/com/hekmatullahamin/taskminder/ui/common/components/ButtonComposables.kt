package com.hekmatullahamin.taskminder.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/**
 * A composable that displays a confirm button typically used in a dialog.
 *
 * The button's text is passed as a string resource ID (`text`), and when clicked, it triggers the
 * provided `action` function.
 *
 * @param text The resource ID of the string to be displayed on the button.
 * @param action A lambda function that is executed when the button is clicked.
 */
@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = stringResource(text))
    }
}

/**
 * A composable that displays a cancel button typically used in a dialog.
 *
 * The button's text is passed as a string resource ID (`text`), and when clicked, it triggers the
 * provided `action` function.
 *
 * @param text The resource ID of the string to be displayed on the button.
 * @param action A lambda function that is executed when the button is clicked.
 */
@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
    Button(
        onClick = action,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = stringResource(text))
    }
}