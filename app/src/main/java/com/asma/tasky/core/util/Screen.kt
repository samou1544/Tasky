package com.asma.tasky.core.util

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object RegisterScreen : Screen("register_screen")
    object AgendaScreen : Screen("agenda_screen")
    object TaskScreen : Screen("task_screen")
    object EventScreen : Screen("event_screen")
    object ReminderScreen : Screen("reminder_screen")
    object EditTitleScreen : Screen("edit_title_screen")
    object EditDescriptionScreen : Screen("edit_Description_screen")
}
