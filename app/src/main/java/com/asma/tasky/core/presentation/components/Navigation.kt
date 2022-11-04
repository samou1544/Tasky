package com.asma.tasky.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asma.tasky.feature_management.agenda.AgendaScreen
import com.asma.tasky.feature_authentication.presentation.login.LoginScreen
import com.asma.tasky.feature_authentication.presentation.register.RegisterScreen
import com.asma.tasky.core.util.Screen
import com.asma.tasky.feature_management.event.EventScreen
import com.asma.tasky.feature_management.reminder.ReminderScreen
import com.asma.tasky.feature_management.task.TaskScreen

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
            TaskScreen()
        }
        composable(Screen.EventScreen.route) {
            EventScreen()
        }
        composable(Screen.ReminderScreen.route) {
            ReminderScreen()
        }
    }
}