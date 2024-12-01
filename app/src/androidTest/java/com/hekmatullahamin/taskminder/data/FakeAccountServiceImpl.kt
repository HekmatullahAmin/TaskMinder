package com.hekmatullahamin.taskminder.data

import com.hekmatullahamin.taskminder.data.model.User
import com.hekmatullahamin.taskminder.data.service.AccountService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * A fake implementation of the [AccountService] interface used for testing purposes.
 * This service simulates user account operations such as authentication, anonymous account creation,
 * linking accounts, deleting accounts, and signing out.
 * It manages the user state using a [StateFlow] and allows for easy testing and mocking.
 */
class FakeAccountServiceImpl @Inject constructor() : AccountService {
    private var _currentUser: MutableStateFlow<User> = MutableStateFlow(User())
    override val currentUser: Flow<User> get() = _currentUser

    private var userId: String = ""
    override val currentUserId: String
        get() = userId

    override val isUserSignedIn: Boolean
        get() = userId.isNotEmpty()

    /**
     * Authenticates a user with the provided email and password, simulating a successful authentication
     * by assigning a `fake-user-id`.
     */
    override suspend fun authenticate(email: String, password: String) {
        userId = "fake-user-id"
        _currentUser.value = User(userId = userId, isAnonymous = false)
    }

    /**
     * Creates an anonymous account for the user, assigning a unique `anonymous-user-id`.
     */
    override suspend fun createAnonymousAccount() {
        userId = "anonymous-user-id"
        _currentUser.value = User(userId = userId, isAnonymous = true)
    }

    /**
     * Links the current anonymous account to an existing user by replacing the `anonymous-user-id`
     * with a `fake-user-id`.
     *
     * @throws IllegalStateException if the account is already linked to a non-anonymous user.
     */
    override suspend fun linkAccount(email: String, password: String) {
        if (userId.isEmpty() || userId == "anonymous-user-id") {
            userId = "fake-user-id"
            _currentUser.value = User(userId = userId, isAnonymous = false)
        } else {
            throw IllegalStateException("Can't link anonymous user")
        }
    }

    /**
     * Deletes the current account by resetting the `userId` and setting the user to anonymous state.
     */
    override suspend fun deleteAccount() {
        userId = ""
        _currentUser.value = User()
    }

    /**
     * Signs out the user. If the user is signed in, it sets the user to anonymous.
     * If the user is already anonymous, it clears the `userId`.
     */
    override suspend fun signOut() {
        if (userId.isNotEmpty()) {
            userId = "anonymous-user-id"
        } else {
            userId = ""
        }
        _currentUser.value = User(userId = userId, isAnonymous = userId == "anonymous-user-id")
    }
}