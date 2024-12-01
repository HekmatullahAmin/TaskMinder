package com.hekmatullahamin.taskminder.data

import com.hekmatullahamin.taskminder.data.service.LogService
import javax.inject.Inject

/**
 * Fake implementation of [LogService] for testing purposes.
 * This implementation logs to the console instead of Firebase Crashlytics.
 */
class FakeLogServiceImpl @Inject constructor() : LogService {

    /**
     * Fake implementation of logging a non-fatal crash or exception.
     * Logs the exception message to the console instead of sending it to Crashlytics.
     *
     * @param throwable The exception or error to be logged.
     */
    override fun logNonFatalCrash(throwable: Throwable) {
        // For testing, we just print the exception message to the console
        println("Fake Log: Logging exception - ${throwable.message}")
    }
}