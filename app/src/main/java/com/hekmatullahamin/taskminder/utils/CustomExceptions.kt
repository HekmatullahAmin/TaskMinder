package com.hekmatullahamin.taskminder.utils

import java.io.IOException

/**
 * Exception thrown when account creation fails.
 *
 * This exception is used to signal errors during account creation, such as validation
 * issues, duplicate accounts, or server-side errors.
 *
 * @param message A message describing the error.
 * @param cause The underlying cause of the exception, if available.
 */
class AccountCreationException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Exception thrown when linking accounts fails.
 *
 * This exception occurs when attempting to link multiple accounts (e.g., social accounts)
 * and an error arises, such as mismatched credentials or network failures.
 *
 * @param message A message describing the error.
 * @param cause The underlying cause of the exception, if available.
 */
class LinkAccountException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Exception thrown when account deletion fails.
 *
 * This exception is triggered when an account deletion operation fails, often due to
 * insufficient permissions, server errors, or the account not existing.
 *
 * @param message A message describing the error.
 * @param cause The underlying cause of the exception, if available.
 */
class AccountDeletionException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Exception thrown when signing out fails.
 *
 * This exception indicates an error during the sign-out process, such as network
 * issues or invalid session data.
 *
 * @param message A message describing the error.
 * @param cause The underlying cause of the exception, if available.
 */
class SignOutException(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Exception thrown for misconfiguration issues.
 *
 * A specialized exception used to represent errors that occur while managing
 * application configuration, such as fetching, parsing, or applying remote or
 * local configuration settings.
 *
 * @param message A message describing the error.
 * @param cause The underlying cause of the exception, if available.
 */
class ConfigurationException(message: String, cause: Throwable? = null) : Exception(message, cause)
