package com.hekmatullahamin.taskminder.di

import com.hekmatullahamin.taskminder.data.repositories.UserPreferencesRepository
import com.hekmatullahamin.taskminder.data.repositories.UserPreferencesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger module responsible for providing repository implementations throughout the application.
 *
 * This module uses the `@Binds` annotation to bind interface implementations to their abstractions,
 * ensuring that dependencies are resolved correctly during dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the implementation of [UserPreferencesRepository] to [UserPreferencesRepositoryImpl].
     *
     * This ensures that whenever an instance of [UserPreferencesRepository] is required,
     * Dagger provides an instance of [UserPreferencesRepositoryImpl].
     *
     * @param userPreferencesRepositoryImpl The concrete implementation of [UserPreferencesRepository].
     * @return An instance of [UserPreferencesRepository] provided by [UserPreferencesRepositoryImpl].
     *
     * @see UserPreferencesRepository
     * @see UserPreferencesRepositoryImpl
     */
    @Binds
    @Singleton
    abstract fun bindUserPreferencesRepository(
        userPreferencesRepositoryImpl: UserPreferencesRepositoryImpl
    ): UserPreferencesRepository
}