package com.hekmatullahamin.taskminder.data.service.impl

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.BuildConfig
import com.google.firebase.remoteconfig.get
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.data.service.ConfigurationService
import com.hekmatullahamin.taskminder.data.service.trace
import com.hekmatullahamin.taskminder.utils.ConfigurationException
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

/**
 * Implementation of [ConfigurationService] that uses Firebase Remote Config
 * to fetch and manage configurations.
 */
class ConfigurationServiceImpl @Inject constructor() : ConfigurationService {

    private val remoteConfig get() = Firebase.remoteConfig

    init {
        // Set up Remote Config settings for debug or production environments
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0 // Set minimum fetch interval to 0 for debug mode
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
    }

    override suspend fun fetchConfiguration(): Boolean {
        return try {
            trace(FETCH_CONFIG_TRACE) {
                remoteConfig.fetchAndActivate().await()
            }
        } catch (e: IOException) {
            throw IOException(
                "Network error occurred while fetching configuration: ${e.message}",
                e
            )
        } catch (e: Exception) {
            throw ConfigurationException("Failed to fetch configuration: ${e.message}", e)
        }
    }

    /**
     * Indicates whether the "Show Alert Option Switch" configuration is enabled.
     */
    override val isShowAlertOptionSwitchConfig: Boolean
        get() = remoteConfig[SHOW_ALERT_OPTION_SWITCH_KEY].asBoolean()

    companion object {
        /**
         * Trace tag for fetching configurations, useful for debugging or monitoring.
         */
        private const val FETCH_CONFIG_TRACE = "fetchConfig"

        /**
         * Key for the "Show Alert Option Switch" configuration.
         */
        private const val SHOW_ALERT_OPTION_SWITCH_KEY = "show_alert_option_switch"
    }
}