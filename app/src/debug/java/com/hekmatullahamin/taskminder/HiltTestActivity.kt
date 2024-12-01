package com.hekmatullahamin.taskminder

import androidx.activity.ComponentActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * A test activity annotated with [@AndroidEntryPoint] to enable Hilt dependency injection
 * for testing purposes.
 *
 * This activity is used in the test environment to initialize Hilt and provide dependency
 * injection for components like ViewModels, services, or repositories. It serves as the entry
 * point for running instrumented tests that require Hilt dependencies.
 *
 * By annotating this class with [@AndroidEntryPoint], Hilt will automatically perform
 * dependency injection for the activity and any other components that require it.
 *
 * Usage:
 * This class can be used in instrumented tests that require Hilt injection to ensure that
 * Hilt dependencies are properly provided to the activity and other components during testing.
 *
 * @see AndroidEntryPoint for more information on how Hilt integrates with Android components.
 */
@AndroidEntryPoint
class HiltTestActivity : ComponentActivity()