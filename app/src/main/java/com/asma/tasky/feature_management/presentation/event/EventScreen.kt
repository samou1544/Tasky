package com.asma.tasky.feature_management.presentation.event

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
import com.asma.tasky.core.presentation.ui.theme.Green
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.core.presentation.util.UiEvent
import com.asma.tasky.core.util.asString
import com.asma.tasky.feature_management.domain.util.DateUtil
import com.asma.tasky.feature_management.presentation.components.*
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
    val timeDialogState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()
    val scrollState = rememberScrollState()
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            viewModel.onEvent(EventEvent.PhotosAdded(uriList.map {
                it.toString()
            }))
        }
    val photos by viewModel.eventPhotos.collectAsState()
    val eventState by viewModel.eventState.collectAsState()
    val taskTime by viewModel.eventTime.collectAsState()
    val reminder by viewModel.eventReminder.collectAsState()
    val goingAttendees by remember {
        derivedStateOf {
            eventState.attendees.filter { attendee ->
                attendee.isGoing
            }
        }
    }

    val notGoingAttendees by remember {
        derivedStateOf {
            eventState.attendees.filter { attendee ->
                !attendee.isGoing
            }
        }
    }

    LaunchedEffect(key1 = true) {

        title?.let {
            viewModel.onEvent(EventEvent.TitleEntered(it))
        }
        deletedImageUrl?.let {
            viewModel.onEvent(EventEvent.PhotoDeleted(it))
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
                text = DateUtil.formatDate(taskTime, "dd MMMM yyyy"),
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            if (eventState.isEditable) {
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
                    if (eventState.isLoading) {
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
                    title = eventState.event.title.ifEmpty { stringResource(id = R.string.new_task) },
                    editable = eventState.isEditable,
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
                    if (eventState.event.description.isNullOrEmpty()) stringResource(id = R.string.task_description)
                    else eventState.event.description!!,
                    editable = eventState.isEditable,
                    onClick = onEditDescription
                )
                AddPhotos(
                    photos = photos,
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

                // time
                Spacer(modifier = Modifier.height(SpaceMedium))
                TimeSelector(
                    label = stringResource(R.string.at),
                    startDateTime = taskTime,
                    editable = eventState.isEditable,
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
                        editable = eventState.isEditable,
                        onClick = {
                            viewModel.onEvent(EventEvent.ToggleReminderDropDown)
                        }
                    )
                    ReminderDropDown(
                        expanded = eventState.showReminderDropDown,
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

                Visitors()

                // Delete
                //todo show delete ownly if I am the creator of the event
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    DeleteText(
                        text = stringResource(R.string.delete_event),
                        onDelete = { viewModel.onEvent(EventEvent.Delete) }
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
                timepicker(initialTime = taskTime.toLocalTime()) {
                    viewModel.onEvent(EventEvent.TimeSelected(it))
                }
            }

            MaterialDialog(
                dialogState = dateDialogState,
                buttons = {
                    positiveButton(stringResource(R.string.dialog_ok))
                    negativeButton(stringResource(R.string.dialog_cancel))
                }
            ) {
                datepicker(initialDate = taskTime.toLocalDate()) { date ->
                    viewModel.onEvent(EventEvent.DateSelected(date))
                }
            }
        }
    }
}
