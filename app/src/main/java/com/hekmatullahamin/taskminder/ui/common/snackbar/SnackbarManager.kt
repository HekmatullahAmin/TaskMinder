package com.hekmatullahamin.taskminder.ui.common.snackbar

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A singleton object that manages snackbar messages to be shown in the UI.
 * It exposes a [StateFlow] to observe snackbar messages and provides methods
 * to show and clear them.
 */
object SnackbarManager {
    // Internal MutableStateFlow holding the current snackbar message, initialized to null.
    private var _messages: MutableStateFlow<SnackbarMessage?> = MutableStateFlow(null)

    // Public read-only StateFlow to observe snackbar messages.
    val snackbarMessages: StateFlow<SnackbarMessage?> get() = _messages.asStateFlow()

    /**
     * Displays a snackbar message using a string resource.
     * This method sets the snackbar message to a resource-based message.
     *
     * @param message The string resource ID to display in the snackbar.
     */
    fun showMessage(@StringRes message: Int) {
        _messages.value = SnackbarMessage.ResourceSnackbar(message)
    }

    /**
     * Displays a custom snackbar message.
     * This method allows the display of any [SnackbarMessage] object.
     *
     * @param snackbarMessage The custom [SnackbarMessage] to display.
     */
    fun showMessage(snackbarMessage: SnackbarMessage) {
        _messages.value = snackbarMessage
    }

    /**
     * Clears the current snackbar message.
     * This will hide any currently visible snackbar message.
     */
    fun clearSnackbarState() {
        _messages.value = null
    }
}