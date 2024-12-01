package com.hekmatullahamin.taskminder.ui.screens.theme

import androidx.lifecycle.viewModelScope
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.model.ThemeColor
import com.hekmatullahamin.taskminder.data.model.ThemeMode
import com.hekmatullahamin.taskminder.data.model.ThemePreferences
import com.hekmatullahamin.taskminder.data.repositories.UserPreferencesRepository
import com.hekmatullahamin.taskminder.data.service.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * The [ThemeViewModel] is responsible for managing and providing the theme preferences for the application.
 * It interacts with the [UserPreferencesRepository] to fetch and update the theme settings, which include
 * the theme color and mode (Light/Dark). The theme preferences are exposed as a [StateFlow] to observe changes
 * in the UI and apply the selected theme color and mode.
 *
 * This view model also provides functions to update the selected theme color and mode by calling the
 * corresponding methods in the repository.
 *
 * @param userPreferencesRepository The repository used to store and retrieve the theme preferences.
 * @param logService The log service used for logging purposes.
 */
@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    logService: LogService
) : TaskMinderViewModel(
    logService = logService
) {
    /**
     * A [StateFlow] that represents the current theme preferences (color and mode) for the application.
     * It is provided by the [userPreferencesRepository] and is observed to update the UI when the theme changes.
     *
     * @see ThemePreferences
     */
    val themeState: StateFlow<ThemePreferences> = userPreferencesRepository.selectedThemeState
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ThemePreferences(
                color = ThemeColor.RED,
                mode = ThemeMode.LIGHT
            )
        )

    /**
     * Updates the theme color in the repository and triggers a UI update.
     * This function is called when the user selects a new theme color.
     *
     * @param themeColor The new theme color to be applied.
     */
    fun setThemeColor(themeColor: ThemeColor) {
        viewModelScope.launch {
            userPreferencesRepository.updateThemeColor(themeColor)
        }
    }

    /**
     * Updates the theme mode (Light or Dark) in the repository and triggers a UI update.
     * This function is called when the user selects a new theme mode.
     *
     * @param themeMode The new theme mode to be applied (either [ThemeMode.LIGHT] or [ThemeMode.DARK]).
     */
    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            userPreferencesRepository.updateThemeMode(themeMode)
        }
    }
}