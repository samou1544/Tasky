package com.asma.tasky.feature_management.presentation.task

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.Green
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.feature_management.domain.Task
import com.asma.tasky.feature_management.domain.util.Reminder
import com.asma.tasky.feature_management.presentation.components.*
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TaskScreen(
    task: Task,
    editable: Boolean = false,
    onEditTitle: (String?) -> (Unit),
    onEditDescription: (String?) -> (Unit)
) {
    val timeDialogState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()

    // todo put the remembered values in the viewModel
    val title by remember {
        mutableStateOf(task.title)
    }
    val description by remember {
        mutableStateOf(task.description)
    }
    var time by remember {
        mutableStateOf(LocalDateTime.ofEpochSecond(task.startDate!! / 1000, 0, ZoneOffset.UTC))
    }
    var reminder by remember {
        mutableStateOf<Reminder>(Reminder.OneHourBefore)
    }
    var showReminderDropDown by remember {
        mutableStateOf(false)
    }
    val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
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
            Text(
                text = LocalDateTime.ofEpochSecond(task.startDate!! / 1000, 0, ZoneOffset.UTC)
                    .format(formatter),
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
                Title(title = title, editable = editable, onClick = onEditTitle)
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium
                    ),
                    thickness = 1.dp, color = Color.LightGray
                )

                // description
                Description(
                    description = description,
                    editable = editable,
                    onClick = { onEditDescription(task.description) }
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
                    startDateTime = time,
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
                            showReminderDropDown = true
                        })
                    ReminderDropDown(
                        expanded = showReminderDropDown,
                        onDismiss = {
                            showReminderDropDown = false
                        },
                        onSelected = {
                            reminder = it
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
                    time = time.with(it)
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
                    time = time.with(date)
                }
            }
        }
    }
}
