package com.hekmatullahamin.taskminder.data.service

/**
 * A service interface for managing remote configurations, including fetching and accessing specific configurations.
 */
interface ConfigurationService {
    /**
     * Fetches the latest configuration from the remote server.
     *
     * @return `true` if the configuration fetch and activation succeeded, `false` otherwise.
     */
    suspend fun fetchConfiguration(): Boolean

    /**
     * Indicates whether the "Show Alert Option Switch" configuration is enabled.
     */
    val isShowAlertOptionSwitchConfig: Boolean
}