package com.asma.tasky.core.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.asma.tasky.authentication.presentation.login.LoginScreen
import com.asma.tasky.authentication.presentation.register.RegisterScreen
import com.asma.tasky.core.util.Screen

@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {

        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onNavigate = { navController.navigate(route = it) }
            )
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen()
        }
        composable(Screen.AgendaScreen.route) {

        }
    }
}