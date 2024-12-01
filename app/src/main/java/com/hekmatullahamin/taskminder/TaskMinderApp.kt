package com.hekmatullahamin.taskminder

import android.content.res.Resources
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.hekmatullahamin.taskminder.data.model.ThemeMode
import com.hekmatullahamin.taskminder.navigation.TaskMinderNavHost
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarManager
import com.hekmatullahamin.taskminder.ui.screens.theme.ThemeViewModel
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme
import kotlinx.coroutines.CoroutineScope

/**
 * The main composable for the TaskMinder application.
 *
 * This function sets up the app's theme, scaffold, and navigation host,
 * creating the overall structure and visual presentation of the app.
 *
 * @param modifier A [Modifier] for applying custom layout or behavior modifications. Defaults to [Modifier].
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskMinderApp(modifier: Modifier = Modifier) {

    // Holds the application state, including navigation and UI scaffold state.
    val appState = rememberAppState()

    // Retrieves the current theme state from the ThemeViewModel.
    val themeViewModel = hiltViewModel<ThemeViewModel>()
    val themeState by themeViewModel.themeState.collectAsStateWithLifecycle()

    // Determines the dark mode and theme color based on the current theme state.
    val isDarkMode = themeState.mode == ThemeMode.DARK
    val themeColor = themeState.color

    // Applies the TaskMinder theme to the app content.
    TaskMinderTheme(isDarkMode = isDarkMode, themeColor = themeColor) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = appState.scaffoldState.snackbarHostState,
                    modifier = Modifier.padding(8.dp),
                    snackbar = { snackbarData ->
                        Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                    }
                )
            }
        ) { innerPadding ->
            TaskMinderNavHost(
                appState = appState,
                modifier = modifier.padding(innerPadding)
            )
        }
    }
}

