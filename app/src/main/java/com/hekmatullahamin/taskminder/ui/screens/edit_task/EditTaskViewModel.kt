package com.hekmatullahamin.taskminder.ui.screens.edit_task

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.model.Task
import com.hekmatullahamin.taskminder.data.service.ConfigurationService
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.data.service.StorageService
import com.hekmatullahamin.taskminder.navigation.EditTaskDestination
import com.hekmatullahamin.taskminder.ui.common.ext.toClockPattern
import com.hekmatullahamin.taskminder.utils.DateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for managing the task editing screen. This ViewModel is responsible for
 * fetching, updating, and saving task details as well as handling UI-related state,
 * such as alert toggling and task save status.
 *
 * @property storageService Service responsible for storing and retrieving tasks.
 * @property configurationService Service responsible for configuration settings, such as alert options.
 */
@HiltViewModel
class EditTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    private val storageService: StorageService,
    private val configurationService: ConfigurationService
) : TaskMinderViewModel(logService = logService) {

    /**
     * Holds the current task being edited.
     * Exposed as a StateFlow for Compose to observe.
     */
    private var _task: MutableStateFlow<Task> = MutableStateFlow(Task())
    val task: StateFlow<Task> get() = _task.asStateFlow()

    //    TODO: Do all StateFlow
    /**
     * Indicates whether the task has been saved.
     * Exposed as a StateFlow for Compose to observe.
     */
    private val _isTaskSaved: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isTaskSaved: StateFlow<Boolean> get() = _isTaskSaved

    /**
     * Indicates whether the alert option is enabled.
     * Exposed as a StateFlow for Compose to observe.
     */
    private val _isAlertEnabled: MutableStateFlow<Boolean> =
        MutableStateFlow(true)
    val isAlertEnabled: StateFlow<Boolean> get() = _isAlertEnabled


    init {
        // Initializes the task by fetching it from the storage service if a task ID is provided
        val taskId = savedStateHandle.get<String>(EditTaskDestination.TASK_ID_ARG)
        if (taskId != null) {
            launchCatching {
                _task.value = storageService.getTask(taskId) ?: Task()
            }
        }
    }

    /**
     * Refreshes the alert toggle based on the configuration service.
     */
    fun refreshAlertEnabled() {
        _isAlertEnabled.value = configurationService.isShowAlertOptionSwitchConfig
    }

    /**
     * Updates the task's due date.
     *
     * @param date The new due date in milliseconds.
     */
    fun onDateChange(date: Long) {
        _task.value = _task.value.copy(dueDate = DateTimeFormatter.convertMillisToDate(date))
    }

    /**
     * Updates the task's due time.
     *
     * @param hour The new hour for the task's due time.
     * @param min The new minute for the task's due time.
     */
    fun onTimeChange(hour: Int, min: Int) {
        val newDueTime = "${hour.toClockPattern()}:${min.toClockPattern()}"
        _task.value = _task.value.copy(dueTime = newDueTime)
    }

    /**
     * Updates the task's title.
     *
     * @param newValue The new title for the task.
     */
    fun onTitleChange(newValue: String) {
        _task.value = _task.value.copy(title = newValue)
    }

    /**
     * Updates the task's description.
     *
     * @param newValue The new description for the task.
     */
    fun onDescriptionChange(newValue: String) {
        _task.value = _task.value.copy(description = newValue)
    }

    /**
     * Updates the task's priority.
     *
     * @param newValue The new priority for the task.
     */
    fun onPriorityChange(newValue: String) {
        _task.value = _task.value.copy(priority = newValue)
    }

    /**
     * Updates the task's alert status.
     *
     * @param newValue The new alert status for the task.
     */
    fun onAlertChange(newValue: Boolean) {
        _task.value = _task.value.copy(alert = newValue)
    }

    /**
     * Saves the current task.
     * If the task is new (id is blank), it adds the task, otherwise it updates the task.
     */
    fun onSaveTask() {
        launchCatching {
            val editedTask = _task.value
            if (editedTask.id.isBlank()) {
                storageService.addTask(editedTask)
            } else {
                storageService.updateTask(editedTask)
            }
            _isTaskSaved.value = true
        }
    }

    /**
     * Resets the task saved status to false after the task is saved.
     */
    fun resetTaskSaved() {
        _isTaskSaved.value = false
    }
}