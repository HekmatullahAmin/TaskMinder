package com.hekmatullahamin.taskminder.ui.screens.settings

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.data.model.ThemeMode
import com.hekmatullahamin.taskminder.ui.common.components.ActionToolbar
import com.hekmatullahamin.taskminder.ui.screens.theme.ThemeViewModel
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme

/**
 * Displays the settings screen where users can navigate to different sections, such as Account and Theme settings,
 * and toggle between dark and light modes.
 *
 * This composable provides a screen layout with options to navigate to an Account screen, Theme settings screen,
 * and toggle the app's theme between dark and light mode. The theme mode is dynamically handled using the `ThemeViewModel`.
 *
 * @param onNavigateUp A lambda function to handle navigation when the user clicks the up button in the top bar.
 * @param onNavigateToAccount A lambda function to handle navigation to the Account settings screen.
 * @param onNavigateToTheme A lambda function to handle navigation to the Theme settings screen.
 * @param modifier A modifier to customize the appearance and layout of the screen.
 */
@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToTheme: () -> Unit,
    modifier: Modifier = Modifier
) {

    // TODO: check if it works
    // Memoize the lambdas to avoid re-creating them on every recomposition
    val onNavigateToAccountMemoized = remember { onNavigateToAccount }
    val onNavigateToThemeMemoized = remember { onNavigateToTheme }
    val onNavigateUpMemoized = remember { onNavigateUp }

    val themeViewModel = hiltViewModel<ThemeViewModel>()
    val themeState by themeViewModel.themeState.collectAsStateWithLifecycle()
    val isDarkMode = themeState.mode == ThemeMode.DARK

    Scaffold(
        topBar = {
            ActionToolbar(
                title = R.string.settings,
//                onNavigateUp = onNavigateUp
                onNavigateUp = onNavigateUpMemoized
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_screen_vertical_spacing)),
            modifier = modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.screen_padding))
        ) {
            SettingsItemRow(
                leadingIcon = R.drawable.person_outline_24,
                text = R.string.account,
                endContent = {
                    Icon(
                        painter = painterResource(R.drawable.navigate_next_24),
                        contentDescription = stringResource(R.string.navigate_to_account_content_description),
//                        modifier = Modifier.clickable { onNavigateToAccount() }
                        modifier = Modifier.clickable { onNavigateToAccountMemoized() }
                    )
                }
            )
            SettingsItemRow(
                leadingIcon = R.drawable.theme,
                text = R.string.theme,
                endContent = {
                    Icon(
                        painter = painterResource(R.drawable.navigate_next_24),
                        contentDescription = stringResource(R.string.navigate_to_theme_content_description),
//                        modifier = Modifier.clickable { onNavigateToTheme() }
                        modifier = Modifier.clickable { onNavigateToThemeMemoized() }
                    )
                }
            )
            SettingsItemRow(
                leadingIcon = R.drawable.sun,
                text = R.string.change_mode,
                endContent = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { themeViewModel.setThemeMode(if (isDarkMode) ThemeMode.LIGHT else ThemeMode.DARK) }
                    )
                }
            )
        }
    }
}

/**
 * Displays a row in the settings screen with an icon, text, and optional content at the end of the row.
 * This composable is used for creating settings items with a consistent layout that can include actions
 * such as navigation or toggles.
 *
 * @param leadingIcon The resource ID of the icon to display at the start of the row.
 * @param text The string resource ID of the text label displayed next to the icon.
 * @param endContent A composable function for the content that appears at the end of the row, such as an icon or a switch.
 * @param modifier A modifier to customize the appearance and layout of the row.
 */
@Composable
fun SettingsItemRow(
    @DrawableRes leadingIcon: Int,
    @StringRes text: Int,
    endContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.settings_row_horizontal_spacing))
    ) {
        Icon(
            painter = painterResource(leadingIcon),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.leading_icon_size))
        )
        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1F)
        )
        endContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemRowPreview() {
    TaskMinderTheme {
        SettingsItemRow(
            leadingIcon = R.drawable.sun,
            text = R.string.account,
            endContent = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    TaskMinderTheme {
        SettingsScreen(
            onNavigateUp = {},
            onNavigateToAccount = {},
            onNavigateToTheme = {},
//            onThemeChangeMode = {}
        )
    }
}