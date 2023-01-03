package com.asma.tasky.core.presentation.components

import android.content.Intent
import android.util.Base64
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
import androidx.navigation.navDeepLink
import coil.ImageLoader
import com.asma.tasky.core.util.Constants
import com.asma.tasky.core.util.Screen
import com.asma.tasky.feature_authentication.presentation.login.LoginScreen
import com.asma.tasky.feature_authentication.presentation.register.RegisterScreen
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.presentation.agenda.AgendaScreen
import com.asma.tasky.feature_management.presentation.edit.EditFieldScreen
import com.asma.tasky.feature_management.presentation.edit.EditableField
import com.asma.tasky.feature_management.presentation.event.EventScreen
import com.asma.tasky.feature_management.presentation.event.photo_details.PhotoDetailsScreen
import com.asma.tasky.feature_management.presentation.reminder.ReminderScreen
import com.asma.tasky.feature_management.presentation.task.TaskScreen

@ExperimentalComposeUiApi
@Composable
fun Navigation(
    navController: NavHostController,
    startDestination: String,
    scaffoldState: ScaffoldState,
    imageLoader: ImageLoader
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
            AgendaScreen(onNavigate = { agendaItem, editable ->
                when (agendaItem) {
                    is AgendaItem.Event -> {}
                    is AgendaItem.Reminder -> {}
                    is AgendaItem.Task -> {
                        navController.navigate(Screen.TaskScreen.route + "?id=${agendaItem.id}&editable=$editable")
                    }
                }
            })
        }

        composable(
            Screen.TaskScreen.route + "?id={id}&editable={editable}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "tasky.asma.com/task/{id}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument("id") {
                    defaultValue = ""
                    type = NavType.StringType
                },
                navArgument("editable") {
                    defaultValue = false
                }
            )
        ) {
            val title =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    Constants.PARAM_TITLE
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
                },
                onEditDescription = {
                    navController.navigate(Screen.EditFieldScreen.route + "/${EditableField.Description.key}/$it")
                },
                onNavigateUp = {
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState
            )
        }
        composable(Screen.EventScreen.route) {
            val title =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    Constants.PARAM_TITLE
                )
            val description =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    Constants.KEY_DESCRIPTION
                )
            val deletedImageUrl =
                navController.currentBackStackEntry?.savedStateHandle?.get<String>(
                    Constants.PARAM_DELETED_IMAGE
                )
            EventScreen(
                title = title,
                description = description,
                deletedImageUrl = deletedImageUrl,
                imageLoader = imageLoader,
                onEditTitle = {
                    navController.navigate(Screen.EditFieldScreen.route + "/${EditableField.Title.key}/$it")
                },
                onEditDescription = {
                    navController.navigate(Screen.EditFieldScreen.route + "/${EditableField.Description.key}/$it")
                },
                onNavigateUp = {
                    navController.popBackStack()
                },
                scaffoldState = scaffoldState,
                openPhotoScreen = { imageUrl ->
                    val encodedImageUrl = "/${
                    Base64.encodeToString(
                        imageUrl.encodeToByteArray(),
                        0
                    )
                    }"
                    navController.navigate(
                        Screen.PhotoDetailScreen.route + encodedImageUrl
                    )
                }
            )
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
        composable(Screen.PhotoDetailScreen.route + "/{imageUrl}") {
            val encodedImageUrl = it.arguments?.getString("imageUrl")!!
            PhotoDetailsScreen(imageLoader = imageLoader, imageUrl = encodedImageUrl, onClose = {
                navController.popBackStack()
            }, onDelete = { imageUrl ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(Constants.PARAM_DELETED_IMAGE, imageUrl)
                navController.popBackStack()
            })
        }
    }
}
