package com.hekmatullahamin.taskminder.ui.common.snackbar

import android.content.res.Resources
import androidx.annotation.StringRes
import com.hekmatullahamin.taskminder.R

/**
 * A sealed class representing different types of snackbar messages.
 * This can be either a simple string or a message loaded from a string resource.
 */
sealed class SnackbarMessage {

    /**
     * Represents a snackbar message that is loaded from a plain text string.
     * @property message The message text to be displayed.
     */
    class StringSnackbar(val message: String) : SnackbarMessage()

    /**
     * Represents a snackbar message that is loaded from a string resource.
     * @property message The resource ID for the message text.
     */
    class ResourceSnackbar(@StringRes val message: Int) : SnackbarMessage()

    companion object {

        /**
         * Converts the [SnackbarMessage] into a string message.
         * If the message is a resource ID, it will fetch the string using the provided [resources].
         *
         * @param resources The [Resources] instance to fetch the string resource.
         * @return A string representation of the snackbar message.
         */
        fun SnackbarMessage.toMessage(resources: Resources): String {
            return when (this) {
                is StringSnackbar -> this.message
                is ResourceSnackbar -> resources.getString(this.message)
            }
        }

        /**
         * Converts a [Throwable] to a [SnackbarMessage].
         * If the exception has a message, it will be used as a [StringSnackbar].
         * If the message is blank or null, a default generic error message will be used.
         *
         * @return A [SnackbarMessage] representing the throwable's message.
         */
        fun Throwable.toSnackbarMessage(): SnackbarMessage {
            val message = this.message.orEmpty()
            return if (message.isNotBlank()) StringSnackbar(message)
            else ResourceSnackbar(R.string.generic_error)
        }
    }
}