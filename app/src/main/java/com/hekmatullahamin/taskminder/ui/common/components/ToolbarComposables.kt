package com.hekmatullahamin.taskminder.ui.common.components

import android.widget.Toolbar
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme

/**
 * A basic toolbar that displays a title centered in the app bar.
 *
 * @param title The string resource ID for the title displayed in the toolbar.
 * @param modifier A [Modifier] to customize the appearance of the toolbar (e.g., padding, size).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicToolbar(
    @StringRes title: Int,
    modifier: Modifier = Modifier
) {
    TopAppBar(title = {
        Text(
            text = stringResource(id = title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    })
}

/**
 * A toolbar with a navigation button (e.g., back button) and a title centered in the app bar.
 *
 * @param title The string resource ID for the title displayed in the toolbar.
 * @param onNavigateUp A lambda function to handle the navigation action (e.g., going back to the previous screen).
 * @param modifier A [Modifier] to customize the appearance of the toolbar (e.g., padding, size).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionToolbar(
    @StringRes title: Int,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title), modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24),
                    contentDescription = stringResource(R.string.back_button_content_description)
                )
            }
        },
        modifier = modifier
    )
}

@Preview
@Composable
private fun BasicToolbarPreview() {
    TaskMinderTheme {
        BasicToolbar(
            title = R.string.create_account
        )
    }
}

@Preview
@Composable
private fun ActionToolbarPreview() {
    TaskMinderTheme {
        ActionToolbar(
            title = R.string.create_new_task,
            onNavigateUp = {}
        )
    }
}