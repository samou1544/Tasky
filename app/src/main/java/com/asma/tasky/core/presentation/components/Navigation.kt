package com.asma.tasky.core.presentation.components

import android.os.Build
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asma.tasky.R
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Screen
import com.asma.tasky.feature_authentication.presentation.login.LoginScreen
import com.asma.tasky.feature_authentication.presentation.register.RegisterScreen
import com.asma.tasky.feature_management.domain.Task
import com.asma.tasky.feature_management.presentation.agenda.AgendaScreen
import com.asma.tasky.feature_management.presentation.edit.EditDescriptionScreen
import com.asma.tasky.feature_management.presentation.edit.EditTitleScreen
import com.asma.tasky.feature_management.presentation.event.EventScreen
import com.asma.tasky.feature_management.presentation.reminder.ReminderScreen
import com.asma.tasky.feature_management.presentation.task.TaskScreen

@ExperimentalComposeUiApi
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    scaffoldState: ScaffoldState,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = { navController.navigate(route = it) },
                onLogin = {
                    navController.popBackStack()
                    navController.navigate(route = Screen.AgendaScreen.route)
                },
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(onNavigate = {
                navController.popBackStack()
            }, scaffoldState = scaffoldState)
        }
        composable(Screen.AgendaScreen.route) {
            AgendaScreen()
        }

        composable(Screen.TaskScreen.route) {
            // todo check if a task object is passed from Agenda Screen
            // todo also check if should show in edit mode

            var title = stringResource(R.string.new_task)
            var description = stringResource(R.string.task_description)

            if (navController.currentBackStackEntry!!.savedStateHandle.contains(Constants.KEY_TITLE)) {
                title =
                    navController.currentBackStackEntry!!.savedStateHandle.get<String>(
                        Constants.KEY_TITLE
                    ) ?: stringResource(R.string.new_task)
            }

            if (navController.currentBackStackEntry!!.savedStateHandle.contains(Constants.KEY_DESCRIPTION)) {
                description =
                    navController.currentBackStackEntry!!.savedStateHandle.get<String>(
                        Constants.KEY_DESCRIPTION
                    ) ?: stringResource(R.string.task_description)
            }

            // default task if no task object passed
            val editable = true
            val task = Task(
                title = title,
                description = description,
                id = "",
                startDate = System.currentTimeMillis()
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                TaskScreen(task = task, editable, onEditTitle = {
                    navController.navigate(Screen.EditTitleScreen.route + "/$it")
                }, onEditDescription = {
                    navController.navigate(Screen.EditDescriptionScreen.route + "/$it")
                })
            }
        }
        composable(Screen.EventScreen.route) {
            EventScreen()
        }
        composable(Screen.ReminderScreen.route) {
            ReminderScreen()
        }
        composable(Screen.EditTitleScreen.route + "/{text}") {
            EditTitleScreen(onClickSave = { title ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.KEY_TITLE, title)
                navController.popBackStack()
            }, onBack = { navController.popBackStack() })
        }

        composable(Screen.EditDescriptionScreen.route + "/{text}") {
            EditDescriptionScreen(onClickSave = { description ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.KEY_DESCRIPTION, description)
                navController.popBackStack()
            }, onBack = { navController.popBackStack() })
        }
    }
}
