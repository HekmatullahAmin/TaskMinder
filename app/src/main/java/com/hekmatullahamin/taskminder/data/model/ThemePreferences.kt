package com.hekmatullahamin.taskminder.data.model

/**
 * Represents the user's theme preferences.
 *
 * @property color The selected theme color.
 * @property mode The selected theme mode (e.g., Light or Dark).
 */
data class ThemePreferences(
    val color: ThemeColor,
    val mode: ThemeMode
)

/**
 * Enum representing available theme colors.
 *
 * @property code The unique integer code representing the color.
 */
enum class ThemeColor(val code: Int) {
    RED(0), GREEN(1), BLUE(2), PURPLE(3);

    companion object {
        /**
         * Retrieves a [ThemeColor] corresponding to the given [code].
         *
         * @param code The integer code of the desired color.
         * @return The matching [ThemeColor], or [RED] if no match is found.
         */
        fun fromValue(code: Int): ThemeColor = when (code) {
            0 -> RED
            1 -> GREEN
            2 -> BLUE
            3 -> PURPLE
            else -> RED
        }
    }
}

/**
 * Enum representing theme modes (e.g., light or dark).
 *
 * @property code The unique integer code representing the mode.
 */
enum class ThemeMode(val code: Int) {
    LIGHT(0), DARK(1);

    companion object {
        /**
         * Retrieves a [ThemeMode] corresponding to the given [code].
         *
         * @param code The integer code of the desired mode.
         * @return The matching [ThemeMode], or [LIGHT] if no match is found.
         */
        fun fromValue(code: Int): ThemeMode = when (code) {
            0 -> LIGHT
            1 -> DARK
            else -> LIGHT
        }
    }
}
