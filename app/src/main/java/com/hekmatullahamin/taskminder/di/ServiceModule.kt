package com.hekmatullahamin.taskminder.di

import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.ConfigurationService
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.data.service.StorageService
import com.hekmatullahamin.taskminder.data.service.impl.AccountServiceImpl
import com.hekmatullahamin.taskminder.data.service.impl.ConfigurationServiceImpl
import com.hekmatullahamin.taskminder.data.service.impl.LogServiceImpl
import com.hekmatullahamin.taskminder.data.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A Dagger module responsible for providing service implementations throughout the application.
 *
 * This module binds service interfaces to their concrete implementations using the `@Binds` annotation,
 * ensuring that dependencies are correctly resolved during dependency injection.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    /**
     * Binds the implementation of [AccountService] to [AccountServiceImpl].
     *
     * This ensures that whenever an instance of [AccountService] is required,
     * Dagger provides an instance of [AccountServiceImpl].
     *
     * @param impl The concrete implementation of [AccountService].
     * @return An instance of [AccountService] provided by [AccountServiceImpl].
     */
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService

    /**
     * Binds the implementation of [LogService] to [LogServiceImpl].
     *
     * This ensures that whenever an instance of [LogService] is required,
     * Dagger provides an instance of [LogServiceImpl].
     *
     * @param impl The concrete implementation of [LogService].
     * @return An instance of [LogService] provided by [LogServiceImpl].
     */
    @Binds
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    /**
     * Binds the implementation of [StorageService] to [StorageServiceImpl].
     *
     * This ensures that whenever an instance of [StorageService] is required,
     * Dagger provides an instance of [StorageServiceImpl].
     *
     * @param impl The concrete implementation of [StorageService].
     * @return An instance of [StorageService] provided by [StorageServiceImpl].
     */
    @Binds
    abstract fun provideStorageService(impl: StorageServiceImpl): StorageService

    /**
     * Binds the implementation of [ConfigurationService] to [ConfigurationServiceImpl].
     *
     * This ensures that whenever an instance of [ConfigurationService] is required,
     * Dagger provides an instance of [ConfigurationServiceImpl].
     *
     * @param impl The concrete implementation of [ConfigurationService].
     * @return An instance of [ConfigurationService] provided by [ConfigurationServiceImpl].
     */
    @Binds
    abstract fun provideConfigurationService(impl: ConfigurationServiceImpl): ConfigurationService
}