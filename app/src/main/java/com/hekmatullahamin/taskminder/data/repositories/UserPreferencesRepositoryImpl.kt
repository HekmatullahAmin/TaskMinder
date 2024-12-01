package com.hekmatullahamin.taskminder.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.hekmatullahamin.taskminder.data.model.ThemeColor
import com.hekmatullahamin.taskminder.data.model.ThemeMode
import com.hekmatullahamin.taskminder.data.model.ThemePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [UserPreferencesRepository] that uses [DataStore] to persist theme preferences.
 *
 * @param dataStore The [DataStore] instance for storing user preferences.
 */
class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    companion object PreferencesKeys {
        /**
         * Key used to store the selected theme color in the [DataStore].
         */
        private val THEME_COLOR_KEY = intPreferencesKey("selected_theme_color")

        /**
         * Key used to store the selected theme mode in the [DataStore].
         */
        private val THEME_MODE_KEY = intPreferencesKey("selected_theme_mode")
    }

    /**
     * A [Flow] that emits the current [ThemePreferences] whenever there is a change in the stored data.
     *
     * If an [IOException] occurs during data retrieval (e.g., corrupted data file), it emits
     * empty preferences as a fallback. Throws other exceptions to be handled by the caller.
     */
    override val selectedThemeState: Flow<ThemePreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val themeColorValue = preferences[THEME_COLOR_KEY] ?: ThemeColor.RED.code
            val themeModeValue = preferences[THEME_MODE_KEY] ?: ThemeMode.LIGHT.code
            ThemePreferences(
                color = ThemeColor.fromValue(themeColorValue),
                mode = ThemeMode.fromValue(themeModeValue)
            )
        }

    /**
     * Updates the selected theme color in the DataStore.
     *
     * @param themeColor The new theme color to be saved.
     */
    override suspend fun updateThemeColor(themeColor: ThemeColor) {
        dataStore.edit { preferences ->
            preferences[THEME_COLOR_KEY] = themeColor.code
        }
    }

    /**
     * Updates the selected theme mode in the DataStore.
     *
     * @param themeMode The new theme mode to be saved.
     */
    override suspend fun updateThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = themeMode.code
        }
    }
}