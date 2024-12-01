package com.hekmatullahamin.taskminder.di

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * A Dagger module responsible for providing Firebase-related dependencies across the application.
 *
 * This module ensures that Firebase services, such as [FirebaseAuth] and [FirebaseFirestore],
 * are readily available for dependency injection throughout the app.
 */
@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    /**
     * Provides a singleton instance of [FirebaseAuth].
     *
     * This instance allows the application to manage user authentication
     * operations such as sign-in, sign-out, and account management.
     *
     * @return A [FirebaseAuth] instance initialized with the default Firebase settings.
     *
     * @sample
     * ```
     * val firebaseAuth: FirebaseAuth = Firebase.auth
     * ```
     */
    @Provides
    fun auth(): FirebaseAuth = Firebase.auth

    /**
     * Provides a singleton instance of [FirebaseFirestore].
     *
     * This instance allows the application to perform Firestore database operations
     * such as adding, retrieving, updating, and deleting documents.
     *
     * @return A [FirebaseFirestore] instance initialized with the default Firebase settings.
     *
     * @sample
     * ```
     * val firestore: FirebaseFirestore = Firebase.firestore
     * ```
     */
    @Provides
    fun firestore(): FirebaseFirestore = Firebase.firestore
}