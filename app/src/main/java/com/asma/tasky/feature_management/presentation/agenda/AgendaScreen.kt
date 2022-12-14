package com.asma.tasky.feature_management.presentation.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import com.asma.tasky.core.domain.util.UserUtil
import com.asma.tasky.core.presentation.ui.theme.LightGray
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.ActionsMenu
import com.asma.tasky.feature_management.presentation.agenda.components.AgendaListItem
import com.asma.tasky.feature_management.presentation.agenda.components.DayItem
import com.asma.tasky.feature_management.presentation.agenda.components.DeleteAlertDialog
import com.asma.tasky.feature_management.presentation.agenda.components.Needle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.util.*

@Composable
fun AgendaScreen(
    viewModel: AgendaViewModel = hiltViewModel(),
    onNavigate: (item: AgendaItem, editable: Boolean) -> Unit
) {
    val state by viewModel.agendaState.collectAsState()
    val dateDialogState = rememberMaterialDialogState()
    var showAlertDialog by remember {
        mutableStateOf(false)
    }


    var selectedItem: AgendaItem by remember {
        mutableStateOf(AgendaItem.Task())
    }

    val pastItems = remember(state.items) {
        state.items.filter { it.startDate < System.currentTimeMillis() / 1000 }
    }

    val futureItems = remember(state.items) {
        state.items.filter { it.startDate >= System.currentTimeMillis() / 1000 }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceMedium, vertical = SpaceSmall)
            ) {
                Button(
                    onClick = { dateDialogState.show() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = state.selectedDate.month.name.capitalize(Locale.ENGLISH),
                            color = Color.White,
                            fontSize = 18.sp
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = stringResource(R.string.drop_down_arrow_content_description),
                            tint = Color.White
                        )
                    }
                }

                if (state.userName.isNotEmpty())
                    Text(
                        text = UserUtil.getInitials(state.userName),
                        modifier = Modifier
                            .padding(SpaceSmall)
                            .background(color = LightGray, shape = CircleShape)
                            .padding(SpaceSmall)
                            .clickable {
                            }
                    )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                    )
            ) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpaceLarge)
                    ) {
                        repeat(7) {
                            val day = state.selectedDate.plusDays(it.toLong())
                            DayItem(
                                modifier = Modifier.weight(1f),
                                selected = day.toLocalDate() == state.selectedDay,
                                day = day,
                                onCLick = { day ->
                                    viewModel.onEvent(AgendaEvent.DaySelected(day.toLocalDate()))
                                }
                            )
                        }
                    }

                    Text(
                        text = if (state.selectedDay == LocalDate.now()) stringResource(
                            R.string.today
                        )
                        else state.selectedDay.dayOfWeek.name,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = SpaceLarge)
                    )

                    pastItems.forEach { item ->
                        AgendaListItem(
                            item = item,
                            onClick = {
                                onNavigate(item, false)
                            },
                            onActionSelected = { action ->
                                when (action) {
                                    is ActionsMenu.Open -> {
                                        onNavigate(item, false)
                                    }
                                    is ActionsMenu.Delete -> {
                                        selectedItem = item
                                        showAlertDialog = true
                                    }
                                    is ActionsMenu.Edit -> {
                                        onNavigate(item, true)
                                    }
                                }
                            },
                            toggleIsDone = {
                                if (item is AgendaItem.Task)
                                    viewModel.onEvent(AgendaEvent.ToggleTaskIsDone(item))
                            }
                        )
                    }
                    Needle()
                    futureItems.forEach { item ->
                        AgendaListItem(
                            item = item,
                            onClick = {
                                onNavigate(item, false)
                            },
                            onActionSelected = { action ->
                                when (action) {
                                    is ActionsMenu.Open -> {
                                        onNavigate(item, false)
                                    }
                                    is ActionsMenu.Delete -> {
                                        selectedItem = item
                                        showAlertDialog = true
                                    }
                                    is ActionsMenu.Edit -> {
                                        onNavigate(item, true)
                                    }
                                }
                            },
                            toggleIsDone = {
                                if (item is AgendaItem.Task)
                                    viewModel.onEvent(AgendaEvent.ToggleTaskIsDone(item))
                            }
                        )
                    }
                }
            }
        }

        MaterialDialog(
            dialogState = dateDialogState,
            buttons = {
                positiveButton(stringResource(R.string.dialog_ok))
                negativeButton(stringResource(R.string.dialog_cancel))
            }
        ) {
            datepicker(initialDate = state.selectedDate.toLocalDate()) { date ->
                viewModel.onEvent(AgendaEvent.DateSelected(date))
            }
        }
        if (showAlertDialog)
            selectedItem.let { item ->
                val text = when (item) {
                    is AgendaItem.Task -> "task"
                    is AgendaItem.Event -> "event"
                    is AgendaItem.Reminder -> "reminder"
                }
                DeleteAlertDialog(
                    onCancel = { showAlertDialog = false },
                    onDelete = {
                        showAlertDialog = false
                        viewModel.onEvent(AgendaEvent.DeleteItem(item))
                    },
                    text = text
                )
            }


    }
}

