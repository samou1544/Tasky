package com.asma.tasky.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Screen
import com.asma.tasky.feature_authentication.presentation.login.LoginScreen
import com.asma.tasky.feature_authentication.presentation.register.RegisterScreen
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.presentation.agenda.AgendaScreen
import com.asma.tasky.feature_management.presentation.edit.EditFieldScreen
import com.asma.tasky.feature_management.presentation.edit.EditableField
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
            AgendaScreen{agendaItem->
                when(agendaItem){
                    is AgendaItem.Event -> {
                    }
                    is AgendaItem.Task -> {
                        navController.navigate(Screen.TaskScreen.route + "?id=${agendaItem.id}?editable=false")
                    }
                    is AgendaItem.Reminder -> {}
                }
            }
        }

        composable(
            Screen.TaskScreen.route + "?id={id}?editable={editable}",
            arguments = listOf(navArgument("id") {
                defaultValue = ""
                type = NavType.StringType
            },
                navArgument("editable") {
                    defaultValue = false
                })
        ) {
            val title =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    Constants.KEY_TITLE
                )
            val description =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    Constants.KEY_DESCRIPTION
                )

            TaskScreen(
                title = title,
                description = description,
                onEditTitle = {
                    navController.navigate(Screen.EditFieldScreen.route + "/${EditableField.Title.key}/$it")
                }, onEditDescription = {
                    navController.navigate(Screen.EditFieldScreen.route + "/${EditableField.Description.key}/$it")
                }, onNavigateUp = {
                    navController.popBackStack()
                }, scaffoldState = scaffoldState)
        }
        composable(Screen.EventScreen.route) {
            EventScreen()
        }
        composable(Screen.ReminderScreen.route) {
            ReminderScreen()
        }
        composable(Screen.EditFieldScreen.route + "/{title}/{text}") {

            EditFieldScreen(onClickSave = { key, text ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(key, text)
                navController.popBackStack()
            }, onBack = { navController.popBackStack() })
        }
    }
}
