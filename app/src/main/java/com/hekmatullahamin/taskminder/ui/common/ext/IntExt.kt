package com.hekmatullahamin.taskminder.ui.common.ext

/**
 * Extension function that formats an integer to a two-digit clock pattern.
 * If the integer is less than 10, a leading zero is added. Otherwise, the number is returned as is.
 *
 * @return A string representation of the integer formatted as a two-digit number.
 */
fun Int.toClockPattern(): String {
    return "%02d".format(this)
}
