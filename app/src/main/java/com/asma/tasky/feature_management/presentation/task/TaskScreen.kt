package com.asma.tasky.feature_management.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.Green
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.feature_management.presentation.components.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.format.DateTimeFormatter

@Composable
fun TaskScreen(
    title: String? = null,
    description: String? = null,
    onEditTitle: (String?) -> (Unit),
    onEditDescription: (String?) -> (Unit),
    viewModel: TaskViewModel = hiltViewModel()
) {
    val timeDialogState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()

    val taskState by viewModel.taskState.collectAsState()
    val taskTime by viewModel.taskTime.collectAsState()
    val reminder by viewModel.taskReminder.collectAsState()
    val editable by viewModel.editModeState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SpaceMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close),
                    tint = Color.White
                )
            }
            val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
            Text(
                text = taskTime.format(formatter),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            if (editable) {
                Text(
                    modifier = Modifier.padding(end = SpaceLarge),
                    text = stringResource(R.string.save),
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
            } else
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit),
                        tint = Color.White
                    )
                }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = SpaceMedium, vertical = SpaceLarge)
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Green, shape = RoundedCornerShape(2.dp))
                    )
                    Text(
                        text = stringResource(R.string.task),
                        modifier = Modifier.padding(start = SpaceSmall),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

                // title
                Title(
                    title = title ?: stringResource(id = R.string.new_task),
                    editable = editable,
                    onClick = onEditTitle
                )
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium
                    ),
                    thickness = 1.dp, color = Color.LightGray
                )

                // description
                Description(
                    description = description ?: stringResource(id = R.string.task_description),
                    editable = editable,
                    onClick = { onEditDescription(description) }
                )
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium
                    ),
                    thickness = 1.dp, color = Color.LightGray
                )

                // time
                Spacer(modifier = Modifier.height(SpaceMedium))
                TimeSelector(
                    label = stringResource(R.string.at),
                    startDateTime = taskTime,
                    editable = editable,
                    onEditDate = {
                        dateDialogState.show()
                    },
                    onEditTime = {
                        timeDialogState.show()
                    }
                )
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium
                    ),
                    thickness = 1.dp, color = Color.LightGray
                )

                // Reminder
                Box {
                    ReminderSelector(
                        reminder = reminder,
                        editable = editable,
                        onClick = {
                            viewModel.onEvent(TaskEvent.ToggleReminderDropDown)
                        })
                    ReminderDropDown(
                        expanded = taskState.showReminderDropDown,
                        onDismiss = {
                            viewModel.onEvent(TaskEvent.ToggleReminderDropDown)
                        },
                        onSelected = {
                            viewModel.onEvent(TaskEvent.ReminderSelected(it))
                        })
                }

                Divider(
                    modifier = Modifier.padding(horizontal = SpaceMedium),
                    thickness = 1.dp, color = Color.LightGray
                )

                // Delete
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    DeleteText(text = "DELETE TASK")
                }
            }

            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                }
            ) {
                timepicker {
                    viewModel.onEvent(TaskEvent.TimeSelected(it))
                }
            }

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton("Ok")
                    negativeButton("Cancel")
                }
            ) {
                datepicker { date ->
                    viewModel.onEvent(TaskEvent.DateSelected(date))
                }
            }
        }
    }
}
