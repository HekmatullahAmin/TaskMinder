package com.hekmatullahamin.taskminder.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger module responsible for providing data source dependencies across the application.
 *
 * This module defines the setup for shared storage mechanisms, such as [DataStore], to
 * manage user preferences or other persistent data.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    /**
     * Provides a singleton instance of [DataStore] configured for user preferences.
     *
     * The [DataStore] instance is backed by a file named `user_preferences` within the app's internal storage.
     *
     * @param context The application [Context], injected by Dagger.
     * @return A [DataStore] instance for managing [Preferences].
     *
     * @sample
     * ```
     * val dataStore: DataStore<Preferences> = provideDataStore(applicationContext)
     * ```
     */

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile("user_preferences")
        }
    }
}