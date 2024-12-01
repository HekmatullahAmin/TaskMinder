package com.hekmatullahamin.taskminder

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * A custom runner for setting up the instrumented application class for tests.
 * This runner overrides the default `newApplication` method to use a custom application class
 * (`HiltTestApplication`) instead of the one specified in the `AndroidManifest.xml`.
 * It ensures that Hilt dependencies are properly injected during tests.
 *
 * This is especially useful when testing Hilt-based applications as it initializes Hilt
 * in the test environment before running the tests.
 *
 * @see HiltTestApplication for the custom application class used in tests.
 */
class CustomTestRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, name: String?, context: Context?): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}