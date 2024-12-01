package com.hekmatullahamin.taskminder.data

import com.hekmatullahamin.taskminder.data.model.Task
import com.hekmatullahamin.taskminder.data.service.StorageService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

/**
 * A fake implementation of [StorageService] used for testing. This service simulates a task storage system
 * where tasks are stored in memory. It allows adding, updating, deleting, and retrieving tasks.
 */
class FakeStorageServiceImpl @Inject constructor() : StorageService {
    private val tasks = mutableMapOf<String, Task>() // Simulated database
    private val currentUserTasks = mutableMapOf<String, MutableList<Task>>() // User-specific tasks

    init {
        // Predefine a task for testing
        val todayDate = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date())
        val predefinedTask = Task(
            id = "1",
            title = "Task 1",
            priority = "High",
            dueDate = todayDate,
            dueTime = "12:00 PM",
            description = "This is a predefined task.",
            completed = false,
            alert = true,
            userId = "anonymous-user-id"
        )

        // Add the predefined task to the maps
        tasks[predefinedTask.id] = predefinedTask
        currentUserTasks.computeIfAbsent(predefinedTask.userId) { mutableListOf() }
            .add(predefinedTask)
    }

    /**
     * Retrieves tasks for the specified [selectedDate] for the current user.
     *
     * @param selectedDate The date for which tasks are to be fetched in MM/dd/yyyy format.
     * @return A flow of tasks that match the selected date.
     */
    override fun getSelectedDayTasks(selectedDate: String): Flow<List<Task>> = flow {
        val userId = "anonymous-user-id" // Simulate fetching tasks for a specific user
        val selectedDayTasks = currentUserTasks[userId]
            ?.filter { it.dueDate == selectedDate }
            ?: emptyList()
        emit(selectedDayTasks)
    }

    /**
     * Retrieves a task by its [taskId].
     *
     * @param taskId The ID of the task to be fetched.
     * @return The task with the specified ID, or null if not found.
     */
    override suspend fun getTask(taskId: String): Task? {
        return tasks[taskId]
    }

    /**
     * Adds a new task to the storage and returns the generated task ID.
     *
     * @param task The task to be added.
     * @return The generated ID for the newly added task.
     */
    override suspend fun addTask(task: Task): String {
        val taskId = UUID.randomUUID().toString() // Generate a unique task ID
        val taskWithId = task.copy(id = taskId)

        tasks[taskId] = taskWithId
        currentUserTasks.computeIfAbsent(task.userId) { mutableListOf() }.add(taskWithId)

        return taskId
    }

    /**
     * Updates an existing task in the storage.
     *
     * @param task The task with updated information.
     * @throws TaskNotFoundException If the task with the provided ID does not exist.
     */
    override suspend fun updateTask(task: Task) {
        tasks[task.id]?.let {
            tasks[task.id] = task // Update the task in the database
            currentUserTasks[task.userId]?.replaceAll { t -> if (t.id == task.id) task else t }
        } ?: throw TaskNotFoundException("Task with ID ${task.id} not found.")
    }

    /**
     * Deletes a task by its [taskId].
     *
     * @param taskId The ID of the task to be deleted.
     * @throws TaskNotFoundException If the task with the provided ID does not exist.
     */
    override suspend fun deleteTask(taskId: String) {
        val task = tasks.remove(taskId)
        task?.let {
            currentUserTasks[task.userId]?.removeIf { it.id == taskId }
        } ?: throw TaskNotFoundException("Task with ID $taskId not found.")
    }

    /**
     * Exception thrown when a task cannot be found.
     *
     * @param message A message describing the error.
     */
    class TaskNotFoundException(message: String) : Exception(message)
}