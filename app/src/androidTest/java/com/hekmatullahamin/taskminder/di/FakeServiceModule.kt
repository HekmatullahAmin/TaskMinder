package com.hekmatullahamin.taskminder.di

import com.hekmatullahamin.taskminder.data.FakeAccountServiceImpl
import com.hekmatullahamin.taskminder.data.FakeConfigurationServiceImpl
import com.hekmatullahamin.taskminder.data.FakeLogServiceImpl
import com.hekmatullahamin.taskminder.data.FakeStorageServiceImpl
import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.ConfigurationService
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.data.service.StorageService
import com.hekmatullahamin.taskminder.data.service.impl.ConfigurationServiceImpl
import com.hekmatullahamin.taskminder.data.service.impl.LogServiceImpl
import com.hekmatullahamin.taskminder.data.service.impl.StorageServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

/**
 * Test module used to provide fake implementations of services for testing purposes.
 * This module replaces the production service module during tests to ensure that
 * fake services are used instead of real ones.
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ServiceModule::class]
)
abstract class FakeServiceModule {

    /**
     * Binds the [FakeAccountServiceImpl] to the [AccountService] interface for testing.
     * This ensures that the fake implementation is used during tests.
     *
     * @param impl The fake implementation of the [AccountService].
     * @return The [AccountService] instance for dependency injection.
     */
    @Binds
    abstract fun provideAccountService(
        impl: FakeAccountServiceImpl
    ): AccountService

    /**
     * Binds the [FakeLogServiceImpl] to the [LogService] interface for testing.
     * This ensures that the fake implementation is used during tests.
     *
     * @param impl The fake implementation of the [LogService].
     * @return The [LogService] instance for dependency injection.
     */
    @Binds
    abstract fun provideLogService(impl: FakeLogServiceImpl): LogService

    /**
     * Binds the [FakeStorageServiceImpl] to the [StorageService] interface for testing.
     * This ensures that the fake implementation is used during tests.
     *
     * @param impl The fake implementation of the [StorageService].
     * @return The [StorageService] instance for dependency injection.
     */
    @Binds
    abstract fun provideStorageService(impl: FakeStorageServiceImpl): StorageService

    /**
     * Binds the [FakeConfigurationServiceImpl] to the [ConfigurationService] interface for testing.
     * This ensures that the fake implementation is used during tests.
     *
     * @param impl The fake implementation of the [ConfigurationService].
     * @return The [ConfigurationService] instance for dependency injection.
     */
    @Binds
    abstract fun provideConfigurationService(impl: FakeConfigurationServiceImpl): ConfigurationService
}