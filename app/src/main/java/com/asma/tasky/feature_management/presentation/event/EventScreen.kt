package com.asma.tasky.feature_management.presentation.event

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.asString
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.presentation.components.*
import com.asma.tasky.feature_management.presentation.event.components.AddAttendeeDialog
import com.asma.tasky.feature_management.presentation.event.components.AddPhotos
import com.asma.tasky.feature_management.presentation.event.components.Visitors
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EventScreen(
    imageLoader: ImageLoader,
    title: String? = null,
    description: String? = null,
    deletedImageUrl: String? = null,
    onEditTitle: (String?) -> (Unit),
    onEditDescription: (String?) -> (Unit),
    onNavigateUp: () -> (Unit),
    openPhotoScreen: (String) -> (Unit),
    scaffoldState: ScaffoldState,
    viewModel: EventViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val startTimeDialogState = rememberMaterialDialogState()
    val endTimeDialogState = rememberMaterialDialogState()

    val startDateDialogState = rememberMaterialDialogState()
    val endDateDialogState = rememberMaterialDialogState()

    val scrollState = rememberScrollState()
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            viewModel.onEvent(EventEvent.PhotosAdded(uriList))
        }
    val state by viewModel.eventState.collectAsState()

    val goingAttendees = remember(state.event.attendees) {
        state.event.attendees.filter { attendee ->
            attendee.isGoing
        }
    }

    val notGoingAttendees = remember(state.event.attendees) {
        state.event.attendees.filter { attendee ->
            !attendee.isGoing
        }
    }

    LaunchedEffect(key1 = true) {

        title?.let {
            viewModel.onEvent(EventEvent.TitleEntered(it))
        }
        deletedImageUrl?.let {
            viewModel.onEvent(EventEvent.PhotoDeleted(Uri.parse(it)))
        }
        description?.let {
            viewModel.onEvent(EventEvent.DescriptionEntered(it))
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
            .background(color = Color.Black)
            .verticalScroll(scrollState),
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
                text = DateUtil.formatDate(state.startTime, "dd MMMM yyyy"),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            if (state.isEditable) {
                Row {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = SpaceLarge)
                            .clickable {
                                viewModel.onEvent(EventEvent.Save)
                            },
                        text = stringResource(R.string.save),
                        color = Color.White,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Normal
                    )
                    if (state.isLoading) {
                        Spacer(modifier = Modifier.width(SpaceSmall))
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            } else
                IconButton(onClick = {
                    viewModel.onEvent(EventEvent.ToggleEditMode)
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
                            .background(color = LightGreen, shape = RoundedCornerShape(2.dp))
                    )
                    Text(
                        text = stringResource(R.string.event),
                        modifier = Modifier.padding(start = SpaceSmall),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

                // title
                Title(
                    title = state.event.title.ifEmpty { stringResource(id = R.string.new_event) },
                    editable = state.isEditable,
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
                    description =
                    if (state.event.description.isNullOrEmpty()) stringResource(id = R.string.event_description)
                    else state.event.description!!,
                    editable = state.isEditable,
                    onClick = onEditDescription
                )
                AddPhotos(
                    photos = state.photos,
                    imageLoader = imageLoader,
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    onClickPhoto = openPhotoScreen
                )
                Divider(
                    modifier = Modifier
                        .padding(
                            horizontal = SpaceMedium
                        )
                        .padding(top = SpaceMedium),
                    thickness = 1.dp, color = Color.LightGray
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                // start time
                TimeSelector(
                    label = stringResource(R.string.from),
                    startDateTime = state.startTime,
                    editable = state.isEditable,
                    onEditDate = {
                        startDateDialogState.show()
                    },
                    onEditTime = {
                        startTimeDialogState.show()
                    }
                )
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium
                    ),
                    thickness = 1.dp, color = Color.LightGray
                )
                // end time
                TimeSelector(
                    label = stringResource(R.string.to),
                    startDateTime = state.endTime,
                    editable = state.isEditable,
                    onEditDate = {
                        endDateDialogState.show()
                    },
                    onEditTime = {
                        endTimeDialogState.show()
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
                        reminder = state.reminder,
                        editable = state.isEditable,
                        onClick = {
                            viewModel.onEvent(EventEvent.ToggleReminderDropDown)
                        }
                    )
                    ReminderDropDown(
                        expanded = state.showReminderDropDown,
                        onDismiss = {
                            viewModel.onEvent(EventEvent.ToggleReminderDropDown)
                        },
                        onSelected = {
                            viewModel.onEvent(EventEvent.ReminderSelected(it))
                        }
                    )
                }

                Divider(
                    modifier = Modifier.padding(horizontal = SpaceMedium),
                    thickness = 1.dp, color = Color.LightGray
                )

                Visitors(
                    selected = state.selectedAttendeeStatus,
                    goingAttendees = goingAttendees,
                    notGoingAttendees = notGoingAttendees,
                    onChangeStatus = {
                        viewModel.onEvent(EventEvent.ChangeStatus(it))
                    },
                    onAddAttendee = {
                        viewModel.onEvent(EventEvent.ToggleShowAddAttendeeDialog)
                    },
                    editable = state.isEditable,
                    onRemoveAttendee = {
                        viewModel.onEvent(EventEvent.AttendeeRemoved(it))
                    }
                )
                Spacer(modifier = Modifier.weight(1f))

                if (state.showDeleteEvent)
                // Delete
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        DeleteText(
                            text = stringResource(R.string.delete_event),
                            onDelete = { viewModel.onEvent(EventEvent.Delete) }
                        )
                    }
            }

            MaterialDialog(
                dialogState = startTimeDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                timepicker(initialTime = state.startTime.toLocalTime()) {
                    viewModel.onEvent(EventEvent.StartTimeSelected(it))
                }
            }

            MaterialDialog(
                dialogState = startDateDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                datepicker(initialDate = state.startTime.toLocalDate()) { date ->
                    viewModel.onEvent(EventEvent.StartDateSelected(date))
                }
            }

            MaterialDialog(
                dialogState = endTimeDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                timepicker(initialTime = state.endTime.toLocalTime()) {
                    viewModel.onEvent(EventEvent.EndTimeSelected(it))
                }
            }

            MaterialDialog(
                dialogState = endDateDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                datepicker(initialDate = state.endTime.toLocalDate()) { date ->
                    viewModel.onEvent(EventEvent.EndDateSelected(date))
                }
            }

            if (state.showAddAttendeeDialog) {
                AddAttendeeDialog(
                    onCancel = { viewModel.onEvent(EventEvent.ToggleShowAddAttendeeDialog) },
                    attendeeEmail = state.attendeeEmail.text,
                    isEmailValid = state.isAttendeeEmailValid,
                    error = state.attendeeEmail.error,
                    onAttendeeEntered = {
                        viewModel.onEvent(EventEvent.AttendeeEmailEntered(it))
                    },
                    onAttendeeAdded = {
                        viewModel.onEvent(EventEvent.AttendeeEmailAdded(it))
                    },
                    isLoading = state.isCheckingAttendeeEmail
                )
            }
        }
    }
}
