package com.hekmatullahamin.taskminder.ui.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hekmatullahamin.taskminder.TaskMinderViewModel
import com.hekmatullahamin.taskminder.data.model.Task
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.data.service.StorageService
import com.hekmatullahamin.taskminder.ui.common.ext.formatDay
import com.hekmatullahamin.taskminder.utils.DateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

/**
 * The ViewModel for managing the tasks UI state and interacting with the StorageService.
 * It provides logic for selecting dates, updating the tasks, and managing the UI state
 * related to tasks for a specific day, month, and year.
 *
 * @property storageService The service responsible for fetching and updating tasks in the storage.
 * @property logService The logging service used for debugging and tracking events.
 */
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val storageService: StorageService,
    logService: LogService
) : TaskMinderViewModel(
    logService = logService
) {

    /**
     * Flow representing the UI state for tasks. It includes the selected
     * date and the list of weekdays and days in the month.
     */
    private var _tasksUiState = MutableStateFlow(TasksUiState())
    val tasksUiState: StateFlow<TasksUiState> = _tasksUiState
        .map { tasksUiState ->
            tasksUiState.copy(
//                selectedDayInMonth = tasksUiState.selectedDayInMonth.padStart(2, '0'),
                selectedDayInMonth = tasksUiState.selectedDayInMonth.formatDay(),
                weekdaysAndDaysInMonth = tasksUiState.weekdaysAndDaysInMonth.map { (weekday, day) ->
//                    weekday to day.padStart(2, '0')
                    weekday to day.formatDay()
                }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TasksUiState()
        )

    /**
     * The list of tasks for the selected date. It uses `flatMapLatest` to fetch tasks based
     * on the current selected month, day, and year from the UI state.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val tasks: StateFlow<List<Task>> =
        tasksUiState.map {
            val selectedMonth = it.selectedMonthIndex.plus(1).toString().formatDay()
            "$selectedMonth/${it.selectedDayInMonth}/${it.selectedYear}"
        }.flatMapLatest { dateString ->
            storageService.getSelectedDayTasks(dateString)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private val selectedMonthIndex get() = tasksUiState.value.selectedMonthIndex
    private val selectedYear get() = tasksUiState.value.selectedYear

    init {
        updateDaysInMonth()
    }

    /**
     * Updates the selected year and refreshes the days in the month.
     *
     * @param year The new year to be selected.
     */
    fun updateSelectedYear(year: Int) {
        _tasksUiState.value = _tasksUiState.value.copy(selectedYear = year)
        updateDaysInMonth()
    }

    /**
     * Selects the next month and updates the days in the selected month.
     */
    fun selectNextMonth() {
        if (selectedMonthIndex < 11) {
            _tasksUiState.value =
                _tasksUiState.value.copy(selectedMonthIndex = selectedMonthIndex + 1)
            updateDaysInMonth()
        }
    }

    /**
     * Selects the previous month and updates the days in the selected month.
     */
    fun selectPreviousMonth() {
        if (selectedMonthIndex > 0) {
            _tasksUiState.value =
                _tasksUiState.value.copy(selectedMonthIndex = selectedMonthIndex - 1)
            updateDaysInMonth()
        }
    }

    /**
     * Updates the selected day in the month.
     *
     * @param day The new day to be selected.
     */
    fun updateSelectDayInMonth(day: String) {
        _tasksUiState.value = _tasksUiState.value.copy(selectedDayInMonth = day)
    }

    /**
     * Gets the number of days in a given month and year.
     *
     * @param month The month (0-based index) for which to get the number of days.
     * @param year The year for which to get the number of days.
     * @return The number of days in the given month of the given year.
     */
    private fun getDaysInMonth(month: Int, year: Int): Int {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    /**
     * Updates the list of weekdays and days in the selected month.
     * It calculates the weekdays for all the days in the selected month and updates the UI state.
     */
    private fun updateDaysInMonth() {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, selectedYear)
            set(Calendar.MONTH, selectedMonthIndex)
        }
        val daysInCurrentMonth =
            getDaysInMonth(selectedMonthIndex, selectedYear)

        val weekdaysAndDaysInSelectedMonth = mutableListOf<Pair<String, String>>()
        for (day in 1..daysInCurrentMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val weekday =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
            weekdaysAndDaysInSelectedMonth.add(
                Pair(
                    weekday!!,
                    day.toString()
                )
            )
        }
        _tasksUiState.value =
            _tasksUiState.value.copy(weekdaysAndDaysInMonth = weekdaysAndDaysInSelectedMonth)
    }

    /**
     * Flags a task as completed or not completed by toggling its `completed` state.
     *
     * @param task The task to flag as completed or not completed.
     */
    fun flagTask(task: Task) {
        launchCatching {
            storageService.updateTask(task.copy(completed = !task.completed))
        }
    }
}