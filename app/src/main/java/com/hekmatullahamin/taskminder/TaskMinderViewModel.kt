package com.hekmatullahamin.taskminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hekmatullahamin.taskminder.data.service.LogService
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarManager
import com.hekmatullahamin.taskminder.ui.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

/**
 * A base ViewModel for managing background tasks and handling exceptions in a centralized way.
 *
 * This ViewModel provides the `launchCatching` function, which launches a coroutine and handles exceptions
 * by showing a snackbar message (optional) and logging the error using the provided [logService].
 *
 * @property logService The service used for logging non-fatal crashes.
 */
open class TaskMinderViewModel(private val logService: LogService) : ViewModel() {

    /**
     * Launches a coroutine that catches any exceptions and handles them by showing a snackbar message
     * (optional) and logging the exception to the [logService].
     *
     * @param snackbar A boolean flag indicating whether the error should be displayed in a snackbar.
     *        Defaults to true.
     * @param block The suspend block of code to execute within the coroutine.
     */
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}