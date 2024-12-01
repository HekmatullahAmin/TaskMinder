package com.hekmatullahamin.taskminder.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Utility object for formatting and converting date and time values.
 *
 * Provides methods for handling common date and time formatting operations.
 */
object DateTimeFormatter {

    /**
     * Converts a timestamp in milliseconds to a formatted date string.
     *
     * @param millis The timestamp in milliseconds to be converted.
     * @return A string representing the formatted date in the pattern "MM/dd/yyyy".
     *
     * Example usage:
     * ```
     * val date = DateTimeFormatter.convertMillisToDate(System.currentTimeMillis())
     * println(date) // Output: "12/01/2024" (depending on the current date)
     * ```
     *
     * Note:
     * - The function uses the default locale of the device to ensure region-appropriate formatting.
     * - Be cautious with locale-specific differences in date representations.
     */
    fun convertMillisToDate(millis: Long): String {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        return formatter.format(Date(millis))
    }
}