package com.hekmatullahamin.taskminder

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * Extension function for [AndroidComposeTestRule] that finds a [SemanticsNodeInteraction]
 * based on a string resource ID. This is useful when you want to test UI elements that display
 * text using string resources.
 *
 * Example usage:
 * ```
 * composeTestRule.onNodeWithStringId(R.string.greeting_text)
 *     .assertIsDisplayed()
 * ```
 *
 * @param id The string resource ID to find the corresponding text in the UI.
 * @return A [SemanticsNodeInteraction] for the node with the given text.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))

/**
 * Extension function for [AndroidComposeTestRule] that finds a [SemanticsNodeInteraction]
 * based on a content description string resource ID. This is useful for testing UI elements
 * like buttons or images that use content descriptions for accessibility.
 *
 * Example usage:
 * ```
 * composeTestRule.onNodeWithContentDescriptionStringId(R.string.button_description)
 *     .assertIsDisplayed()
 *     .performClick()
 * ```
 *
 * @param id The string resource ID of the content description.
 * @return A [SemanticsNodeInteraction] for the node with the given content description.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithContentDescriptionStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithContentDescription(activity.getString(id))