package com.hekmatullahamin.taskminder.ui.screens.theme

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.data.model.ThemeColor
import com.hekmatullahamin.taskminder.data.model.ThemeMode
import com.hekmatullahamin.taskminder.ui.common.components.ActionToolbar
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme
import com.hekmatullahamin.taskminder.ui.theme.bluePrimaryDark
import com.hekmatullahamin.taskminder.ui.theme.bluePrimaryLight
import com.hekmatullahamin.taskminder.ui.theme.greenPrimaryDark
import com.hekmatullahamin.taskminder.ui.theme.greenPrimaryLight
import com.hekmatullahamin.taskminder.ui.theme.purplePrimaryDark
import com.hekmatullahamin.taskminder.ui.theme.purplePrimaryLight
import com.hekmatullahamin.taskminder.ui.theme.redPrimaryDark
import com.hekmatullahamin.taskminder.ui.theme.redPrimaryLight
import com.hekmatullahamin.taskminder.ui.theme.silver

/**
 * A screen that displays a list of theme color options for the user to choose from.
 * The selected theme mode (Light or Dark) affects the available colors, which are displayed
 * in a series of [ThemeView] items. The user can select a theme color, which is then applied
 * to the app's UI by updating the [ThemeViewModel].
 *
 * @param onNavigateUp A lambda function to be called when the user navigates back from the screen.
 * @param modifier Modifier to be applied to the overall layout of the screen.
 */
@Composable
fun ThemeScreen(
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeViewModel = hiltViewModel<ThemeViewModel>()
    val themeState by themeViewModel.themeState.collectAsStateWithLifecycle()
    val selectedThemeMode = themeState.mode
    val selectedThemeColor = getThemeColor(selectedThemeMode, themeState.color)
//    val selectedThemeColor = when (themeState.mode) {
//        ThemeMode.LIGHT -> {
//            when (themeState.color) {
//                ThemeColor.RED -> redPrimaryLight
//                ThemeColor.GREEN -> greenPrimaryLight
//                ThemeColor.BLUE -> bluePrimaryLight
//                ThemeColor.PURPLE -> purplePrimaryLight
//            }
//
//        }
//
//        ThemeMode.DARK -> {
//            when (themeState.color) {
//                ThemeColor.RED -> redPrimaryDark
//                ThemeColor.GREEN -> greenPrimaryDark
//                ThemeColor.BLUE -> bluePrimaryDark
//                ThemeColor.PURPLE -> purplePrimaryDark
//            }
//        }
//    }

    val themeColors = if (selectedThemeMode == ThemeMode.LIGHT) {
        listOf(
            redPrimaryLight, greenPrimaryLight, bluePrimaryLight, purplePrimaryLight
        )
    } else {
        listOf(
            redPrimaryDark, greenPrimaryDark, bluePrimaryDark, purplePrimaryDark
        )
    }

    Scaffold(
        topBar = {
            ActionToolbar(
                title = R.string.theme,
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.theme_screen_vertical_spacing)),
            modifier = modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.screen_padding))
        ) {
            themeColors.forEachIndexed { index, color ->
                ThemeView(
                    themeColor = color,
                    isThemeSelected = selectedThemeColor == color,
                    onThemeSelected = { themeViewModel.setThemeColor(ThemeColor.fromValue(index)) }
                )
            }
        }
    }
}

/**
 * Displays a single theme option with a preview of the color. When selected,
 * it triggers a callback to update the app's selected theme color.
 * The theme color is represented as a [Surface] with a color preview,
 * and an icon is shown when the theme is selected.
 *
 * @param themeColor The color to be displayed in the theme preview.
 * @param isThemeSelected A boolean value indicating if this theme is the currently selected one.
 * @param onThemeSelected A lambda function that is triggered when the user selects this theme.
 * @param modifier Modifier to be applied to the layout of the theme view.
 */
@Composable
fun ThemeView(
    themeColor: Color,
    isThemeSelected: Boolean,
    onThemeSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .height(dimensionResource(R.dimen.theme_view_height))
            .clickable { onThemeSelected() },
        shape = RoundedCornerShape(dimensionResource(R.dimen.theme_view_corner_radius)),
        shadowElevation = dimensionResource(R.dimen.theme_view_shadow_elevation)
    ) {
        Box {
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    color = themeColor,
                    size = Size(
                        width = size.width,
                        height = size.height / 3F
                    )
                )
                drawCircle(
                    color = silver,
                    radius = size.width / 12F,
                    center = Offset(x = size.width / 6F, y = size.height / 1.5F)
                )
                drawRect(
                    color = silver,
                    size = Size(
                        width = size.width / 1.6F,
                        height = size.height / 20F
                    ),
                    topLeft = Offset(x = size.width / 3.5F, y = size.height / 2F)
                )
                drawRect(
                    color = silver,
                    size = Size(
                        width = size.width / 3F,
                        height = size.height / 20F
                    ),
                    topLeft = Offset(x = size.width / 3.5F, y = size.height / 1.5F)
                )
                drawRect(
                    color = silver,
                    size = Size(
                        width = size.width / 2F,
                        height = size.height / 20F
                    ),
                    topLeft = Offset(x = size.width / 3.5F, y = size.height / 1.2F)
                )
            }
            if (isThemeSelected) {
                Image(
                    painter = painterResource(R.drawable.check_circle_24),
                    contentDescription = stringResource(R.string.select_theme_content_description),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(dimensionResource(R.dimen.theme_view_check_image_size))
                        .padding(
                            top = dimensionResource(R.dimen.theme_view_check_image_padding_top),
                            end = dimensionResource(R.dimen.theme_view_check_image_padding_end)
                        )
                )
            }
        }
    }
}

/**
 * Returns the appropriate theme color based on the selected theme mode (Light or Dark)
 * and the selected theme color (Red, Green, Blue, or Purple).
 *
 * @param mode The current theme mode, either [ThemeMode.LIGHT] or [ThemeMode.DARK].
 * @param color The selected theme color, one of [ThemeColor.RED], [ThemeColor.GREEN],
 *              [ThemeColor.BLUE], or [ThemeColor.PURPLE].
 *
 * @return The corresponding color for the selected mode and color.
 */
private fun getThemeColor(mode: ThemeMode, color: ThemeColor): Color {
    return when (mode) {
        ThemeMode.LIGHT -> when (color) {
            ThemeColor.RED -> redPrimaryLight
            ThemeColor.GREEN -> greenPrimaryLight
            ThemeColor.BLUE -> bluePrimaryLight
            ThemeColor.PURPLE -> purplePrimaryLight
        }
        ThemeMode.DARK -> when (color) {
            ThemeColor.RED -> redPrimaryDark
            ThemeColor.GREEN -> greenPrimaryDark
            ThemeColor.BLUE -> bluePrimaryDark
            ThemeColor.PURPLE -> purplePrimaryDark
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true)
@Composable
private fun ThemeScreenPreview() {
    TaskMinderTheme {
        ThemeScreen(
//            selectedThemeColor = redPrimaryLight,
//            onThemeSelected = {},
            onNavigateUp = {}
        )
    }
}

@Preview
@Composable
private fun ThemeViewPreview() {
    TaskMinderTheme {
        ThemeView(
            themeColor = redPrimaryLight,
            isThemeSelected = true,
            onThemeSelected = {}
        )
    }
}