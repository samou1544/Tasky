package com.asma.tasky.core.presentation.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.asma.tasky.R

@Composable
fun CustomScaffold(
    showFAB: Boolean = true,
    state: ScaffoldState,
    onFabClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (showFAB) {
                FloatingActionButton(
                    backgroundColor = MaterialTheme.colors.primary,
                    onClick = onFabClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add_item)
                    )
                }
            }
        },
        scaffoldState = state,
        floatingActionButtonPosition = FabPosition.End

    ) {
    }
}