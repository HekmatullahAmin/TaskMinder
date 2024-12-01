package com.hekmatullahamin.taskminder.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hekmatullahamin.taskminder.HiltTestActivity
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.assertCurrentRouteName
import com.hekmatullahamin.taskminder.data.FakeAccountServiceImpl
import com.hekmatullahamin.taskminder.onNodeWithContentDescriptionStringId
import com.hekmatullahamin.taskminder.onNodeWithStringId
import com.hekmatullahamin.taskminder.rememberAppState
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Instrumented test class for verifying navigation flows in the app.
 *
 * This class contains tests that validate the navigation logic between various screens in the app,
 * such as the Tasks screen, Settings screen, Account screen, and more. The tests check that navigation
 * actions like button clicks or menu selections correctly navigate to the expected screens and that
 * the content on those screens matches the expected values.
 *
 * The tests use [HiltAndroidTest] for dependency injection, [HiltAndroidRule] to set up Hilt
 * dependencies, and [ComposeTestRule] for composing and testing UI components. The tests are run
 * on an Android device or emulator using [AndroidJUnit4] as the test runner.
 */
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NavigationTest {

    /**
     * Hilt rule for setting up and injecting dependencies in the test.
     */
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    /**
     * Rule for setting up the Compose test environment.
     */
    @get: Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()
    private lateinit var navController: TestNavHostController

    /**
     * Setup for the test, initializes the NavController and sets up the initial content.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Before
    fun setup() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            val appState = rememberAppState(navController = navController)
            TaskMinderTheme {
                TaskMinderNavHost(appState = appState)
            }
        }
    }

    /**
     * Tests the navigation from the Tasks screen to the Settings screen when the Settings button is clicked.
     */
    @Test
    fun taskScreen_onSettingsClick_navigatesToSettingsScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateToSettingsScreen()
            composeTestRule.onNodeWithStringId(R.string.settings)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Settings screen to the Account screen when the "Navigate to Account"
     * option is clicked.
     */
    @Test
    fun settingsScreen_onNavigateToAccountClick_navigatesToAccountScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateToAccountScreen()
            composeTestRule.onNodeWithStringId(R.string.account)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Account screen to the Splash screen when the "Sign Out" button is clicked.
     * (Test logic needs to be implemented)
     */
    @Test
    fun accountScreen_onSingOutClick_navigatesToSplashScreen() {

    }

    /**
     * Tests the navigation from the Account screen to the Splash screen when the "Delete Account" button
     * is clicked. (Test logic needs to be implemented)
     */
    @Test
    fun accountScreen_onDeleteMyAccountClick_navigatesToSplashScreen() {

    }

    /**
     * Tests the navigation from the Settings screen to the Theme screen when the "Navigate to Theme" option
     * is clicked.
     */
    @Test
    fun settingsScreen_onNavigateToThemeClick_navigatesToThemeScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateToThemeScreen()
            composeTestRule.onNodeWithStringId(R.string.theme)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Tasks screen to the Edit Task screen when the "Add New Task" button
     * is clicked. Verifies that the "Create New Task" title is displayed.
     */
    @Test
    fun tasksScreen_onAddNewTaskClick_navigatesToEditTaskScreen_displaysCreateNewTaskTitle() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            onAddNewTaskFloatingActionButtonClick_navigateToEditTaskScreen()
            composeTestRule.onNodeWithStringId(R.string.create_new_task)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Tasks screen to the Edit Task screen when a task (e.g., "Task 1") is clicked.
     * Verifies that the "Edit Task" screen is displayed with the correct task information.
     */
    @Test
    fun tasksScreen_onTaskClick_navigatesToEditTaskScreen_displaysEditTaskTitle() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            // Simulate clicking on a specific task (e.g., "Task 1")
            composeTestRule.onNodeWithText("Task 1").performClick()

            navController.assertCurrentRouteName(EditTaskDestination.routeWithArgs)

            composeTestRule.onRoot().printToLog("nodes")
            // Verify the title of the screen is "Edit Task"
            composeTestRule.onNodeWithText("Task 1")
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("This is a predefined task.")
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Login screen to the Tasks screen after signing in.
     */
    @Test
    fun loginScreen_onSignInClick_navigatesToTaskScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateFromLoginScreenToSplashScreen()
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navController.assertCurrentRouteName(TasksDestination.route)
        }
    }

    /**
     * Tests the navigation from the Sign Up screen to the Tasks screen after creating an account.
     */
    @Test
    fun signUpScreen_onCreateAccountClick_navigatesToTaskScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateFromSignUpScreenToSplashScreen()
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navController.assertCurrentRouteName(TasksDestination.route)
        }
    }

    /**
     * Tests the navigation from the Edit Task screen back to the Tasks screen when the "Navigate Up" button
     * is clicked.
     */
    @Test
    fun editTaskScreen_onNavigateUpClick_navigatesBackToTasksScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateBackFromEditTaskScreenToTasksScreen()
            composeTestRule.onNodeWithStringId(R.string.tasks)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Settings screen back to the Tasks screen when the "Navigate Up" button
     * is clicked.
     */
    @Test
    fun settingsScreen_onNavigateUpClick_navigatesBackToTasksScreen() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateBackFromSettingsScreenToTasksScreen()
            composeTestRule.onNodeWithStringId(R.string.tasks)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Account screen back to the Settings screen when the "Navigate Up" button
     * is clicked.
     */
    @Test
    fun accountScreen_onNavigateUpClick_navigatesBackToSettings() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateBackFromAccountScreenToSettingsScreen()
            composeTestRule.onNodeWithStringId(R.string.settings)
                .assertIsDisplayed()
        }
    }

    /**
     * Tests the navigation from the Theme screen back to the Settings screen when the "Navigate Up" button
     * is clicked.
     */
    @Test
    fun themeScreen_onNavigateUpClick_navigatesBackToSettings() {
        composeTestRule.run {
            waitForSplashScreenToFinishAndNavigateToTasksScreen()
            navigateBackFromThemeScreenToSettingsScreen()
            composeTestRule.onNodeWithStringId(R.string.settings)
                .assertIsDisplayed()
        }
    }

    // Helper methods for navigation actions
    private fun waitForSplashScreenToFinishAndNavigateToTasksScreen(waitingTime: Long = 1000L) {
        composeTestRule.waitUntil(waitingTime) {
            navController.currentDestination?.route == TasksDestination.route
        }
    }

    private fun navigateToSettingsScreen() {
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.navigate_to_settings_content_description)
            .performClick()
        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    private fun navigateToThemeScreen() {
        navigateToSettingsScreen()
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.navigate_to_theme_content_description)
            .performClick()
        navController.assertCurrentRouteName(ThemeDestination.route)
    }

    private fun navigateToAccountScreen() {
        navigateToSettingsScreen()
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.navigate_to_account_content_description)
            .performClick()
        navController.assertCurrentRouteName(AccountDestination.route)
    }

    private fun navigateToLoginScreen() {
        navigateToAccountScreen()
        composeTestRule.onNodeWithStringId(R.string.sign_in)
            .performClick()
        navController.assertCurrentRouteName(LoginDestination.route)
    }

    private fun navigateToSignUpScreen() {
        navigateToAccountScreen()
        composeTestRule.onNodeWithStringId(R.string.create_account)
            .performClick()
        navController.assertCurrentRouteName(SignUpDestination.route)
    }

    private fun navigateFromLoginScreenToSplashScreen() {
        navigateToLoginScreen()
        composeTestRule.onNodeWithStringId(R.string.email)
            .performTextInput("hekmat@gmail.com")
        composeTestRule.onNodeWithStringId(R.string.password)
            .performTextInput("Hekmat@1")
        composeTestRule.onNodeWithStringId(R.string.sign_in)
            .performClick()
    }

    private fun navigateFromSignUpScreenToSplashScreen() {
        navigateToSignUpScreen()
        composeTestRule.onNodeWithStringId(R.string.email)
            .performTextInput("hekmat@gmail.com")
        composeTestRule.onNodeWithStringId(R.string.password)
            .performTextInput("Hekmat@1")
        composeTestRule.onNodeWithStringId(R.string.repeat_password)
            .performTextInput("Hekmat@1")
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.create_account_button_content_description)
            .assertHasClickAction()
            .performClick()
    }

    private fun onAddNewTaskFloatingActionButtonClick_navigateToEditTaskScreen() {
        composeTestRule.run {
            composeTestRule.onNodeWithContentDescriptionStringId(R.string.floating_action_button_add_content_description)
                .performClick()
            navController.assertCurrentRouteName(EditTaskDestination.routeWithArgs)
        }
    }

    private fun navigateBackFromEditTaskScreenToTasksScreen() {
        onAddNewTaskFloatingActionButtonClick_navigateToEditTaskScreen()
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.back_button_content_description)
            .performClick()
        navController.assertCurrentRouteName(TasksDestination.route)
    }

    private fun navigateBackFromSettingsScreenToTasksScreen() {
        navigateToSettingsScreen()
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.back_button_content_description)
            .performClick()
        navController.assertCurrentRouteName(TasksDestination.route)
    }

    private fun navigateBackFromAccountScreenToSettingsScreen() {
        navigateToAccountScreen()
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.back_button_content_description)
            .performClick()
        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    private fun navigateBackFromThemeScreenToSettingsScreen() {
        navigateToThemeScreen()
        composeTestRule.onNodeWithContentDescriptionStringId(R.string.back_button_content_description)
            .performClick()
        navController.assertCurrentRouteName(SettingsDestination.route)
    }
}

