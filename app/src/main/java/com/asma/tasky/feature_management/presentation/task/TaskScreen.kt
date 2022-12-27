package com.asma.tasky.feature_management.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.asString
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.presentation.components.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TaskScreen(
    title: String? = null,
    description: String? = null,
    onEditTitle: (String?) -> (Unit),
    onEditDescription: (String?) -> (Unit),
    onNavigateUp: () -> (Unit),
    scaffoldState: ScaffoldState,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val timeDialogState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()

    val taskState by viewModel.taskState.collectAsState()

    LaunchedEffect(key1 = true) {

        title?.let {
            viewModel.onEvent(TaskEvent.TitleEntered(it))
        }
        description?.let {
            viewModel.onEvent(TaskEvent.DescriptionEntered(it))
        }
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
                is UiEvent.NavigateUp -> {
                    onNavigateUp()
                }
                else -> {}
            }
        }
    }
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
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.close),
                    tint = Color.White
                )
            }
            Text(
                text = DateUtil.formatDate(taskState.taskTime, "dd MMMM yyyy"),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            if (taskState.isEditable) {
                Row {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = SpaceLarge)
                            .clickable {
                                viewModel.onEvent(TaskEvent.Save)
                            },
                        text = stringResource(R.string.save),
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Normal
                    )
                    if (taskState.isLoading) {
                        Spacer(modifier = Modifier.width(SpaceSmall))
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            } else
                IconButton(onClick = {
                    viewModel.onEvent(TaskEvent.ToggleEditMode)
                }) {
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
                    title = taskState.task.title.ifEmpty { stringResource(id = R.string.new_task) },
                    editable = taskState.isEditable,
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
                    description = if (taskState.task.description.isNullOrEmpty()) stringResource(id = R.string.task_description) else taskState.task.description!!,
                    editable = taskState.isEditable,
                    onClick = onEditDescription
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
                    startDateTime = taskState.taskTime,
                    editable = taskState.isEditable,
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
                        reminder = taskState.taskReminder,
                        editable = taskState.isEditable,
                        onClick = {
                            viewModel.onEvent(TaskEvent.ToggleReminderDropDown)
                        }
                    )
                    ReminderDropDown(
                        expanded = taskState.showReminderDropDown,
                        onDismiss = {
                            viewModel.onEvent(TaskEvent.ToggleReminderDropDown)
                        },
                        onSelected = {
                            viewModel.onEvent(TaskEvent.ReminderSelected(it))
                        }
                    )
                }

                Divider(
                    modifier = Modifier.padding(horizontal = SpaceMedium),
                    thickness = 1.dp, color = Color.LightGray
                )

                // Delete
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    DeleteText(
                        text = stringResource(R.string.delete_task),
                        onDelete = { viewModel.onEvent(TaskEvent.Delete) }
                    )
                }
            }

            MaterialDialog(
                dialogState = timeDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                timepicker(initialTime = taskState.taskTime.toLocalTime()) {
                    viewModel.onEvent(TaskEvent.TimeSelected(it))
                }
            }

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                datepicker(initialDate = taskState.taskTime.toLocalDate()) { date ->
                    viewModel.onEvent(TaskEvent.DateSelected(date))
                }
            }
        }
    }
}
