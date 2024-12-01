package com.hekmatullahamin.taskminder.utils

import androidx.compose.ui.res.stringResource
import com.hekmatullahamin.taskminder.R

/**
 * A centralized object for storing constant values used throughout the application.
 *
 * This object contains values that are reused in multiple places, promoting consistency
 * and ease of maintenance. All constants within this object should be immutable and declared
 * with `const` to ensure compile-time evaluation.
 *
 * Example usage:
 * ```
 * delay(Constants.SPLASH_SCREEN_TIMEOUT)
 * ```
 */
object Constants {

    /**
     * The duration (in milliseconds) for which the splash screen is displayed.
     *
     * This value is used to delay the navigation from the splash screen to the main application
     * screen. The default timeout is set to 1000 milliseconds (1 second).
     */
    const val SPLASH_SCREEN_TIMEOUT = 1000L
}