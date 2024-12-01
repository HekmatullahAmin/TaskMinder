package com.hekmatullahamin.taskminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main entry point for the TaskMinder application.
 *
 * This activity is responsible for initializing the app and setting up the content view.
 * It uses the Hilt dependency injection framework and enables edge-to-edge rendering.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskMinderApp()
        }
    }
}