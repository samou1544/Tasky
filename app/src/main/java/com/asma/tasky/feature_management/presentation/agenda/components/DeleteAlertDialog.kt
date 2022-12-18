package com.asma.tasky.feature_management.presentation.agenda.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.asma.tasky.core.presentation.ui.theme.Green
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge

@Composable
fun DeleteAlertDialog(onCancel: () -> Unit, onDelete: () -> Unit, text: String) {
    AlertDialog(
        title = {
            Text("Delete $text")
        },
        text = {
            Text("Are you sure you want to delete this $text?")
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ) {


                Button(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(SpaceLarge))
                Button(
                    onClick = onDelete,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Green,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "OK")
                }
            }
        },
        onDismissRequest = { onCancel() }
    )
}
