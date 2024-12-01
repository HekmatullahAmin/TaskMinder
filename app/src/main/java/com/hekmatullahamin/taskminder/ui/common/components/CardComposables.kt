package com.hekmatullahamin.taskminder.ui.common.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hekmatullahamin.taskminder.R

/**
 * A composable representing a "dangerous" card editor with a highlighted color.
 * This card editor uses the primary color from the theme for the title and icon.
 *
 * @param title The resource ID of the string to be displayed as the card's title.
 * @param icon The resource ID of the drawable to be displayed as the card's icon.
 * @param modifier A [Modifier] to customize the card's appearance (e.g., padding, size).
 * @param onEditClick A lambda function that will be invoked when the card is clicked.
 */
@Composable
fun DangerousCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(
        title = title,
        icon = icon,
        onEditClick = onEditClick,
        highlightColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    )
}

/**
 * A composable representing a "regular" card editor with a more neutral highlighted color.
 * This card editor uses the "onSurface" color from the theme for the title and icon.
 *
 * @param title The resource ID of the string to be displayed as the card's title.
 * @param icon The resource ID of the drawable to be displayed as the card's icon.
 * @param modifier A [Modifier] to customize the card's appearance (e.g., padding, size).
 * @param onEditClick A lambda function that will be invoked when the card is clicked.
 */
@Composable
fun RegularCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(
        title = title,
        icon = icon,
        onEditClick = onEditClick,
        highlightColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
    )
}

/**
 * A composable representing a card editor that can be used for both dangerous and regular actions.
 * Displays a title, an icon, and a click listener for editing.
 *
 * @param title The resource ID of the string to be displayed as the card's title.
 * @param icon The resource ID of the drawable to be displayed as the card's icon.
 * @param onEditClick A lambda function that will be invoked when the card is clicked.
 * @param highlightColor The color to be used for the title and icon, passed as a parameter.
 * @param modifier A [Modifier] to customize the card's appearance (e.g., padding, size).
 */
@Composable
private fun CardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.card_elevation)),
        modifier = modifier,
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.card_inner_padding))
        ) {
            Text(
                stringResource(title),
                color = highlightColor,
                modifier = Modifier.weight(1F)
            )

            Icon(
                painter = painterResource(icon),
                contentDescription = "Icon",
                tint = highlightColor
            )
        }
    }
}