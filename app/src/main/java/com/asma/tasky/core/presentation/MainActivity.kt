package com.asma.tasky.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = rememberScaffoldState()
                    Scaffold(
                        floatingActionButton = {
                            if (shouldShowFAB(navBackStackEntry)) {
                                FloatingActionButton(
                                    backgroundColor = MaterialTheme.colors.primary,
                                    onClick = {

                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = stringResource(id = R.string.add_item)
                                    )
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
