package com.hekmatullahamin.taskminder.ui.common.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Adds customizable padding to the card-like modifier. By default, it applies
 * 16.dp horizontal padding (left and right) and 0.dp vertical padding (top)
 * with 8.dp bottom padding.
 *
 * @return A [Modifier] with the applied padding.
 */
fun Modifier.cardModifier(): Modifier {
    return this.padding(start = 16.dp, top = 0.dp, end = 16.dp, bottom = 8.dp)
}

/**
 * Modifier for fields to fill the maximum width and have horizontal and vertical padding.
 *
 * @return A [Modifier] for a field with horizontal padding of 10.dp and vertical padding of 4.dp.
 */
fun Modifier.fieldModifier(): Modifier {
    return this
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 4.dp)
}

/**
 * Modifier for buttons to fill the maximum width and have horizontal and vertical padding.
 *
 * @return A [Modifier] for a button with horizontal padding of 10.dp and vertical padding of 8.dp.
 */
fun Modifier.buttonModifier(): Modifier {
    return this
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 8.dp)
}