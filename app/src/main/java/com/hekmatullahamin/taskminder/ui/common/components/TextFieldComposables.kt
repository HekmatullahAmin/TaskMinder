package com.hekmatullahamin.taskminder.ui.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.hekmatullahamin.taskminder.R

/**
 * A composable representing an email input field with a leading icon for email.
 *
 * @param value The current value of the email field.
 * @param onNewValue A lambda function to handle the change in value (e.g., updating the email).
 * @param modifier A [Modifier] to customize the appearance of the email field (e.g., padding, size).
 */
@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(R.string.email)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = stringResource(R.string.email),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}

/**
 * A composable representing a password input field with a visibility toggle.
 *
 * @param value The current value of the password field.
 * @param placeholder The resource ID of the placeholder text to be displayed in the password field.
 * @param onNewValue A lambda function to handle the change in value (e.g., updating the password).
 * @param modifier A [Modifier] to customize the appearance of the password field (e.g., padding, size).
 */
@Composable
fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(R.drawable.visibility_on_24)
        else painterResource(R.drawable.visibility_off_24)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = icon,
                    contentDescription = if (isVisible) stringResource(R.string.password_field_end_icon_hide_content_description)
                    else stringResource(R.string.password_field_end_icon_show_content_description)
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}