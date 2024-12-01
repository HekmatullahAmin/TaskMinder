package com.hekmatullahamin.taskminder.data.service

import com.hekmatullahamin.taskminder.data.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * A service interface for managing tasks stored in a remote database.
 */
interface StorageService {

    /**
     * Retrieves a list of tasks for the specified date.
     *
     * @param selectedDate The date for which to retrieve tasks, formatted as a string.
     * @return A [Flow] emitting a list of tasks matching the specified date.
     */
    fun getSelectedDayTasks(selectedDate: String): Flow<List<Task>>

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param taskId The unique identifier of the task.
     * @return The [Task] if found, or `null` if not found.
     */
    suspend fun getTask(taskId: String): Task?

    /**
     * Adds a new task to the database.
     *
     * @param task The task to be added.
     * @return The unique identifier of the newly added task.
     */
    suspend fun addTask(task: Task): String

    /**
     * Updates an existing task in the database.
     *
     * @param task The task with updated details.
     */
    suspend fun updateTask(task: Task)

    /**
     * Deletes a task by its unique identifier.
     *
     * @param taskId The unique identifier of the task to be deleted.
     */
    suspend fun deleteTask(taskId: String)
}