package com.asma.tasky.core.util

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object AgendaScreen : Screen("main_feed_screen")
}
