package com.hekmatullahamin.taskminder.data.service.impl

import com.google.firebase.Firebase
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import com.hekmatullahamin.taskminder.data.service.LogService
import javax.inject.Inject

/**
 * Implementation of [LogService] that uses Firebase Crashlytics to log non-fatal crashes.
 */
class LogServiceImpl @Inject constructor() : LogService {

    /**
     * Logs a non-fatal crash or exception to Firebase Crashlytics.
     * If logging fails, it silently handles the error to avoid app crashes.
     *
     * @param throwable The exception or error to be logged.
     */
    override fun logNonFatalCrash(throwable: Throwable) {
        try {
            Firebase.crashlytics.recordException(throwable)
        }catch (e: Exception){
            // Fallback: Handle failure to log to Crashlytics (e.g., log to console or ignore)
            println("Failed to log exception to Crashlytics: ${e.message}")
        }
    }
}