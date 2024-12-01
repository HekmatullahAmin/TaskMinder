package com.hekmatullahamin.taskminder

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

/**
 * Asserts that the current route in the [NavController] matches the expected route name.
 *
 * This function is an extension on [NavController] used in tests to verify that the current
 * route in the navigation stack matches a specific route name. It can be useful for checking
 * that the navigation state is correct after a certain navigation action has been performed.
 *
 * @param expectedRouteName The expected route name to compare with the current route in the [NavController].
 *
 * @throws AssertionError If the current route does not match the expected route name.
 *
 * @see NavController for more information on how to use the navigation controller in Jetpack Compose.
 */
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}