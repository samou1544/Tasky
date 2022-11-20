package com.asma.tasky.core.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.asma.tasky.R
import com.asma.tasky.core.presentation.components.Navigation
import com.asma.tasky.core.presentation.ui.theme.TaskyTheme
import com.asma.tasky.core.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isAuthenticating.value
            }
        }
        setContent {
            TaskyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val listItems = arrayOf("Event", "Task", "Reminder")
                    val showMenu by viewModel.showMenu.collectAsState()
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = rememberScaffoldState()
                    Scaffold(
                        floatingActionButton = {
                            if (shouldShowFAB(navBackStackEntry)) {
                                Box(modifier = Modifier) {
                                    FloatingActionButton(
                                        backgroundColor = Color.Black,
                                        shape = RoundedCornerShape(16.dp),
                                        onClick = {
                                            viewModel.setShowMenu(true)
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = stringResource(id = R.string.add_item),
                                            tint = Color.White
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = showMenu,
                                        offset = DpOffset(x = (0).dp, y = 10.dp),
                                        onDismissRequest = {
                                            viewModel.setShowMenu(false)
                                        }
                                    ) {
                                        listItems.forEachIndexed { index, itemValue ->
                                            DropdownMenuItem(
                                                onClick = {
                                                    viewModel.setShowMenu(false)
                                                    when (index) {
                                                        0 -> navController.navigate(Screen.EventScreen.route + "?editable=true")
                                                        1 -> navController.navigate(Screen.TaskScreen.route + "?editable=true")
                                                        2 -> navController.navigate(Screen.ReminderScreen.route + "?editable=true")
                                                    }
                                                }
                                            ) {
                                                Text(text = itemValue)
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        scaffoldState = scaffoldState,
                        floatingActionButtonPosition = FabPosition.End

                    ) {
                        val isLoggedIn by viewModel.isLoggedIn.collectAsState()
                        Navigation(
                            navController,
                            startDestination = if (isLoggedIn) Screen.AgendaScreen.route else Screen.LoginScreen.route,
                            scaffoldState
                        )
                    }
                }
            }
        }
    }

    private fun shouldShowFAB(backStackEntry: NavBackStackEntry?): Boolean {
        return backStackEntry?.destination?.route in listOf(
            Screen.AgendaScreen.route
        )
    }
}
