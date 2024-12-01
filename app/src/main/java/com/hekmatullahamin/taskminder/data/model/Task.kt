package com.hekmatullahamin.taskminder.data.model

import com.google.firebase.firestore.DocumentId

/**
 * Represents a task with associated details.
 *
 * @property id Unique identifier for the task, assigned by Firestore.
 * @property title Title or name of the task.
 * @property priority Priority level of the task (e.g., HIGH, MEDIUM, LOW).
 * @property dueDate The date by which the task should be completed.
 * @property dueTime The time by which the task should be completed.
 * @property description A detailed description of the task.
 * @property completed Indicates whether the task has been completed.
 * @property alert Indicates whether the user should receive an alert for this task.
 * @property userId Identifier of the user associated with the task.
 */
data class Task(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val alert: Boolean = false,
    val userId: String = ""
)
