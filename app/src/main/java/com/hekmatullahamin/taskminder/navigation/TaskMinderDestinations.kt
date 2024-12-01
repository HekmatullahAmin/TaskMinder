package com.hekmatullahamin.taskminder.navigation

import androidx.navigation.NavType
import androidx.navigation.NavType.Companion.StringType
import androidx.navigation.navArgument

/**
 * Represents a navigation destination in the TaskMinder app.
 * Each destination corresponds to a screen or feature in the app, with a unique route identifier.
 */
interface TaskMinderDestination {
    /**
     * The route associated with the destination, used for navigation purposes.
     * This route is typically used in a navigation graph to define the screen's path.
     */
    val route: String
}

/**
 * A destination representing the splash screen of the app.
 * This is the initial screen that appears when the app is launched.
 */
object SplashDestination : TaskMinderDestination {
    override val route: String = "splash"
}

/**
 * A destination representing the login screen of the app.
 * This screen allows users to sign in with their credentials.
 */
object LoginDestination : TaskMinderDestination {
    override val route: String = "login"
}

/**
 * A destination representing the sign-up screen of the app.
 * This screen allows users to create a new account by providing necessary details.
 */
object SignUpDestination : TaskMinderDestination {
    override val route: String = "signup"
}

/**
 * A destination representing the tasks overview screen.
 * This screen shows a list of tasks and their statuses.
 */
object TasksDestination : TaskMinderDestination {
    override val route: String = "tasks"
}

/**
 * A destination representing the screen for adding or editing a specific task.
 * This screen allows users to add details of a new task or modify the details of an existing task.
 */
object EditTaskDestination : TaskMinderDestination {
    override val route: String = "edit_task"

    /**
     * The argument key used to pass the task ID in the route.
     */
    const val TASK_ID_ARG = "taskId"

    /**
     * The route pattern that includes the task ID as a dynamic argument.
     */
    val routeWithArgs = "$route/{$TASK_ID_ARG}"

    /**
     * List of navigation arguments for the edit task screen.
     * This argument is used to pass the task ID for editing.
     */
    val arguments = listOf(
        navArgument(TASK_ID_ARG) {
            type = StringType
            nullable = true
            defaultValue = null
        }
    )
}

/**
 * A destination representing the settings screen of the app.
 * This screen allows users to configure app preferences.
 */
object SettingsDestination : TaskMinderDestination {
    override val route: String = "settings"
}

/**
 * A destination representing the account screen of the app.
 * This screen allows users to view and edit their account information.
 */
object AccountDestination : TaskMinderDestination {
    override val route: String = "account"
}

/**
 * A destination representing the theme settings screen of the app.
 * This screen allows users to customize the appearance of the app, such as dark or light mode.
 */
object ThemeDestination : TaskMinderDestination {
    override val route: String = "theme"
}