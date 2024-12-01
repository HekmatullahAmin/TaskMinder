package com.hekmatullahamin.taskminder

import android.content.res.Resources
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarManager
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

/**
 * Manages the application state for TaskMinder, providing access to navigation,
 * scaffolding, and snackbar functionality in a centralized manner.
 *
 * @property scaffoldState The [BottomSheetScaffoldState] used to manage bottom sheets and snackbars.
 * @property navController The [NavHostController] used for app navigation.
 * @property snackbarManager The [SnackbarManager] responsible for managing snackbar messages.
 * @property resources The [Resources] object used to access app resources such as strings and dimensions.
 * @property coroutineScope The [CoroutineScope] for launching asynchronous operations tied to this app state.
 */
@OptIn(ExperimentalMaterial3Api::class)
class TaskMinderAppState(
    val scaffoldState: BottomSheetScaffoldState,
    val navController: NavHostController,
    val snackbarManager: SnackbarManager,
    val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                scaffoldState.snackbarHostState.showSnackbar(message = text)
                snackbarManager.clearSnackbarState()
            }
        }
    }

    /**
     * Pops the top entry from the back stack, navigating to the previous destination.
     */
    fun popUp() {
        navController.popBackStack()
    }

    /**
     * Navigates to the specified [route].
     *
     * @param route The destination route to navigate to.
     */
    fun navigate(route: String) {
        navController.navigate(route = route) {
            launchSingleTop = true
        }
    }

    /**
     * Navigates to a specified [route] while clearing the back stack up to a given [popUpToRoute].
     *
     * @param route The destination route to navigate to.
     * @param popUpToRoute The route to clear the back stack up to. This route is also removed.
     */
    fun navigateSingleTopToAndPopupTo(
        route: String,
        popUpToRoute: String
    ) {
        navController.navigate(route) {
            popUpTo(route = popUpToRoute) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }
}

/**
 * Creates and remembers an instance of [TaskMinderAppState], which manages the app's
 * navigation, scaffolding, and snackbar interactions.
 *
 * @param scaffoldState The [BottomSheetScaffoldState] to manage bottom sheets and snackbars. Defaults to a new instance.
 * @param navController The [NavHostController] to handle app navigation. Defaults to a new instance.
 * @param snackbarManager The [SnackbarManager] for handling snackbar messages. Defaults to a singleton instance.
 * @param resources The [Resources] object for accessing app resources. Defaults to the current [LocalContext]'s resources.
 * @param coroutineScope The [CoroutineScope] for launching asynchronous operations. Defaults to a new [CoroutineScope].
 *
 * @return An instance of [TaskMinderAppState] tied to the current composition.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberAppState(
    scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        TaskMinderAppState(
            scaffoldState = scaffoldState,
            navController = navController,
            snackbarManager = snackbarManager,
            resources = resources,
            coroutineScope = coroutineScope
        )
    }
