package com.hekmatullahamin.taskminder.data.model

/**
 * Represents a user in the system.
 *
 * @property userId Unique identifier for the user. Could be an empty string for unauthenticated users.
 * @property isAnonymous Indicates whether the user is anonymous.
 */
data class User(
    val userId: String = "",
    val isAnonymous: Boolean = true
)
