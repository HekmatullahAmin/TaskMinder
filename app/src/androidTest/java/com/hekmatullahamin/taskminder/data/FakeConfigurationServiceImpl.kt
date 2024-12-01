package com.hekmatullahamin.taskminder.data

import com.hekmatullahamin.taskminder.data.service.ConfigurationService
import javax.inject.Inject

/**
 * Fake implementation of [ConfigurationService] for testing purposes.
 * This implementation simulates fetching configuration without using Firebase Remote Config.
 */
class FakeConfigurationServiceImpl @Inject constructor() : ConfigurationService {

    /**
     * Fake implementation of fetching configuration.
     * This returns true as if the configuration was successfully fetched.
     */
    override suspend fun fetchConfiguration(): Boolean {
        // Simulate a successful configuration fetch
        println("Fake Configuration: Simulated configuration fetch successful.")
        return true
    }

    /**
     * Fake implementation of checking whether the "Show Alert Option Switch" configuration is enabled.
     * This returns a hardcoded value for testing purposes.
     */
    override val isShowAlertOptionSwitchConfig: Boolean
        get() = true // Return true as if the config is enabled
}