package com.hekmatullahamin.taskminder.data.service

import com.hekmatullahamin.taskminder.data.model.User
import kotlinx.coroutines.flow.Flow

/**
 * A service interface for managing user accounts, including authentication,
 * account linking, deletion, and sign-out functionality.
 */
interface AccountService {

    /**
     * A [Flow] representing the current user. Emits updates whenever the user changes
     * (e.g., authentication state updates or user switches).
     */
    val currentUser: Flow<User>

    /**
     * The unique identifier of the currently authenticated user.
     * Returns an empty string if no user is signed in.
     */
    val currentUserId: String

    /**
     * Indicates whether a user is currently signed in.
     *
     * @return `true` if a user is signed in, `false` otherwise.
     */
    val isUserSignedIn: Boolean

    /**
     * Authenticates a user using their email and password.
     */
    suspend fun authenticate(email: String, password: String)

    /**
     * Creates an anonymous account for the user.
     *
     * This can be used for temporary access without requiring an email or password.
     */
    suspend fun createAnonymousAccount()

    /**
     * Links an email and password to the current anonymous account.
     *
     * Converts an anonymous account into a full account with email and password credentials.
     */

    suspend fun linkAccount(email: String, password: String)

    /**
     * Deletes the current user's account.
     */
    suspend fun deleteAccount()

    /**
     * Signs out the current user.
     */
    suspend fun signOut()
}