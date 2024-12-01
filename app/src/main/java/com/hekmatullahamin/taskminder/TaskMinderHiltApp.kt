package com.hekmatullahamin.taskminder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * The application class for the TaskMinder app, annotated with `@HiltAndroidApp` to enable
 * dependency injection using Hilt.
 *
 * This class serves as the entry point for setting up Hilt in the application. It ensures that
 * Hilt generates and maintains the necessary components for dependency injection throughout the app.
 *
 * Usage:
 * - Add this class to the `AndroidManifest.xml` file as the application name:
 *   `<application android:name=".TaskMinderHiltApp" ... />`
 *
 * Annotations:
 * - `@HiltAndroidApp`: Marks this class as the Hilt application entry point.
 */
@HiltAndroidApp
class TaskMinderHiltApp : Application() {
}