package com.hekmatullahamin.taskminder.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.io.File
import java.util.UUID
import javax.inject.Singleton
import javax.sql.DataSource

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataSourceModule::class]
)
object TestDataSourceModule {

    /**
     * Provides a test instance of [DataStore<Preferences>] for use in tests.
     * This [DataStore] is backed by a temporary file, which is deleted after the JVM shuts down.
     * This allows for isolated tests without persisting data across test runs.
     *
     * @return A [DataStore<Preferences>] instance for testing purposes.
     */
    @Provides
    @Singleton
    fun provideTestDataSource(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create {
            File.createTempFile("test_preferences", ".preferences_pb").apply { deleteOnExit() }
        }
    }
}