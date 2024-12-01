package com.hekmatullahamin.taskminder.data.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObject
import com.hekmatullahamin.taskminder.data.model.Task
import com.hekmatullahamin.taskminder.data.service.AccountService
import com.hekmatullahamin.taskminder.data.service.StorageService
import com.hekmatullahamin.taskminder.data.service.trace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

/**
 * Implementation of [StorageService] using Firebase Firestore for storing and managing tasks.
 *
 * @param fireStore The [FirebaseFirestore] instance for interacting with Firestore.
 * @param firebaseAuth The [AccountService] instance for accessing the current user.
 */
class StorageServiceImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val firebaseAuth: AccountService
) : StorageService {

    /**
     * Retrieves a list of tasks for a specific date for the currently authenticated user.
     *
     * @param selectedDate The date for which to retrieve tasks, formatted as a string ["MM/dd/yyyy"].
     * @return A [Flow] emitting a list of tasks matching the specified date.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSelectedDayTasks(selectedDate: String): Flow<List<Task>> {
        return firebaseAuth.currentUser.flatMapLatest { user ->
            fireStore.collection(TASKS_COLLECTION)
                .whereEqualTo(USER_ID_FIELD, user.userId)
                .whereEqualTo(TASK_DATE_FIELD, selectedDate)
                .dataObjects()
        }
    }

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param taskId The unique identifier of the task.
     * @return The [Task] if found, or `null` if the task does not exist.
     */
    override suspend fun getTask(taskId: String): Task? =
        fireStore.collection(TASKS_COLLECTION).document(taskId).get().await().toObject()

    /**
     * Adds a new task to the database for the currently authenticated user.
     *
     * @param task The task to be added.
     * @return The unique identifier of the newly added task.
     */
    override suspend fun addTask(task: Task): String =
        trace(SAVE_TASK_TRACE) {
            val taskWithUserId = task.copy(userId = firebaseAuth.currentUserId)
            fireStore.collection(TASKS_COLLECTION).add(taskWithUserId).await().id
        }

    /**
     * Updates an existing task in the database.
     *
     * @param task The task with updated details. The task must have a valid `id` field.
     */
    override suspend fun updateTask(task: Task): Unit =
        trace(UPDATE_TASK_TRACE) {
            fireStore.collection(TASKS_COLLECTION).document(task.id).set(task).await()
        }

    /**
     * Deletes a task by its unique identifier from the database.
     *
     * @param taskId The unique identifier of the task to be deleted.
     */
    override suspend fun deleteTask(taskId: String) {
        fireStore.collection(TASKS_COLLECTION).document(taskId).delete().await()
    }

    companion object {
        /**
         * The name of the Firestore collection where tasks are stored.
         */
        private const val TASKS_COLLECTION = "tasks"

        /**
         * The field name in the Firestore task documents that stores the user's unique identifier.
         */
        private const val USER_ID_FIELD = "userId"

        /**
         * The field name in the Firestore task documents that stores the due date of the task.
         */
        private const val TASK_DATE_FIELD = "dueDate"

        /**
         * The trace name used for monitoring and debugging task-saving operations.
         */
        private const val SAVE_TASK_TRACE = "saveTask"

        /**
         * The trace name used for monitoring and debugging task-updating operations.
         */
        private const val UPDATE_TASK_TRACE = "updateTask"
    }
}