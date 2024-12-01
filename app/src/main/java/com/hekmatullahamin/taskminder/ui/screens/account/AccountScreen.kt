package com.hekmatullahamin.taskminder.ui.screens.account

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hekmatullahamin.taskminder.R
import com.hekmatullahamin.taskminder.ui.common.components.ActionToolbar
import com.hekmatullahamin.taskminder.ui.common.components.DangerousCardEditor
import com.hekmatullahamin.taskminder.ui.common.components.DialogCancelButton
import com.hekmatullahamin.taskminder.ui.common.components.DialogConfirmButton
import com.hekmatullahamin.taskminder.ui.common.components.RegularCardEditor
import com.hekmatullahamin.taskminder.ui.common.ext.cardModifier
import com.hekmatullahamin.taskminder.ui.theme.TaskMinderTheme

/**
 * Composable that represents the Account Screen.
 * Handles login, sign-up, account sign-out, and account deletion.
 *
 * @param onLoginClick Callback for the login button.
 * @param onSignUpClick Callback for the sign-up button.
 * @param onNavigateUp Callback for the navigation up action.
 * @param onSignOutClick Callback for the sign-out action.
 * @param onDeleteMyAccountClick Callback for the delete account action.
 */
@Composable
fun AccountScreen(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onNavigateUp: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit
) {
    val accountViewModel: AccountViewModel = hiltViewModel()
    val uiState by accountViewModel.uiState.collectAsStateWithLifecycle()
    val isAccountSignedOut by accountViewModel.isAccountSignedOut
    val isAccountDeleted by accountViewModel.isAccountDeleted

    //    TODO: check if it works properly
    LaunchedEffect(isAccountSignedOut || isAccountDeleted) {
        when {
            isAccountSignedOut -> {
                onSignOutClick() // Handle sign-out logic (e.g., navigate to login screen)
                accountViewModel.resetIsAccountSignedOut() // Clear the state to prevent redundant triggers
            }

            isAccountDeleted -> {
                onDeleteMyAccountClick() // Handle account deletion logic
                accountViewModel.resetIsAccountDeleted() // Clear the state to prevent redundant triggers
            }
        }
    }
//    LaunchedEffect(isAccountDeleted, isAccountSignedOut) {
//        if (isAccountSignedOut || isAccountDeleted) {
//            if (isAccountSignedOut) {
//                onSignOutClick()
//                accountViewModel.resetIsAccountSignedOut()
//            } else {
//                onDeleteMyAccountClick()
//                accountViewModel.resetIsAccountDeleted()
//            }
//        }
//    }

    AccountScreenContent(
        uiState = uiState,
        onNavigateUp = onNavigateUp,
        onLoginClick = onLoginClick,
        onSignUpClick = onSignUpClick,
        onSignOutClick = accountViewModel::signOutFromAccount,
        onDeleteMyAccountClick = accountViewModel::deleteMyAccount
    )
}

/**
 * Displays the account screen content with options for login, sign-up,
 * account sign-out, and account deletion based on the user's state.
 *
 * @param uiState The current state of the account (e.g., anonymous).
 * @param modifier Modifier for the root container.
 * @param onNavigateUp Callback for the navigation up action.
 * @param onLoginClick Callback for the login button.
 * @param onSignUpClick Callback for the sign-up button.
 * @param onSignOutClick Callback for the sign-out action.
 * @param onDeleteMyAccountClick Callback for the delete account action.
 */
@Composable
fun AccountScreenContent(
    modifier: Modifier = Modifier,
    uiState: AccountUiState,
    onNavigateUp: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onDeleteMyAccountClick: () -> Unit
) {
    Scaffold(
        topBar = {
            ActionToolbar(
                title = R.string.account,
                onNavigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            if (uiState.isAnonymousAccount) {
                RegularCardEditor(
                    R.string.sign_in,
                    R.drawable.person_outline_24,
                    modifier = Modifier.cardModifier()
                ) {
                    onLoginClick()
                }

                RegularCardEditor(
                    R.string.create_account,
                    R.drawable.person_add_alt_24,
                    modifier = Modifier.cardModifier()
                ) {
                    onSignUpClick()
                }
            } else {
                SignOutCard { onSignOutClick() }
                DeleteMyAccountCard { onDeleteMyAccountClick() }
            }
        }
    }
}

/**
 * Composable for displaying the sign-out card with a confirmation dialog.
 *
 * @param modifier Modifier for the card.
 * @param onSignOutClick Callback for confirming the sign-out action.
 */
@Composable
private fun SignOutCard(
    modifier: Modifier = Modifier,
    onSignOutClick: () -> Unit
) {
    var showWarningDialog by remember { mutableStateOf(false) }

    RegularCardEditor(
        title = R.string.sign_out,
        icon = R.drawable.exit_24,
        modifier = Modifier.cardModifier()
    ) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.sign_out_title)) },
            text = { Text(stringResource(R.string.sign_out_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.sign_out) {
                    onSignOutClick()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

/**
 * Composable for displaying the delete account card with a confirmation dialog.
 *
 * @param modifier Modifier for the card.
 * @param onDeleteMyAccountClick Callback for confirming the delete account action.
 */
@Composable
private fun DeleteMyAccountCard(
    modifier: Modifier = Modifier,
    onDeleteMyAccountClick: () -> Unit
) {
    var showWarningDialog by remember { mutableStateOf(false) }

    DangerousCardEditor(
        title = R.string.delete_my_account,
        icon = R.drawable.delete_outline_24,
        modifier = Modifier.cardModifier()
    ) {
        showWarningDialog = true
    }

    if (showWarningDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.delete_account_title)) },
            text = { Text(stringResource(R.string.delete_account_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.delete_my_account) {
                    onDeleteMyAccountClick()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true)
@Composable
fun AccountScreenPreview() {
    val uiState = AccountUiState(isAnonymousAccount = false)

    TaskMinderTheme {
        AccountScreenContent(
            uiState = uiState,
            onNavigateUp = { },
            onLoginClick = { },
            onSignUpClick = { },
            onSignOutClick = { },
            onDeleteMyAccountClick = { }
        )
    }
}
