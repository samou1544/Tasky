package com.asma.tasky.feature_management.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.domain.util.ActionsMenu
import com.asma.tasky.feature_management.domain.util.DateUtil
import java.time.format.DateTimeFormatter

@Composable
fun AgendaListItem(
    item: AgendaItem,
    onClick: () -> (Unit),
    onActionSelected: (ActionsMenu) -> Unit,
    toggleIsDone: () -> Unit
) {
    val backgroundColor = when (item) {
        is AgendaItem.Event -> LightGreen
        is AgendaItem.Reminder -> LightGray
        is AgendaItem.Task -> Green
    }
    val textColor = when (item) {
        is AgendaItem.Task -> Color.White
        else -> Color.Black
    }
    val tintColor = textColor
    val actions = arrayOf(ActionsMenu.Open, ActionsMenu.Edit, ActionsMenu.Delete)
    var showActionsMenu by remember {
        mutableStateOf(false)
    }
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceSmall)
            .background(
                color = backgroundColor, shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SpaceMedium,
                    top = SpaceMedium,
                    end = SpaceSmall,
                    bottom = SpaceMedium
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            val isDone = (item is AgendaItem.Task && item.isDone)
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable(enabled = item is AgendaItem.Task) {
                            toggleIsDone()
                        },
                    imageVector = if (isDone) Icons.Outlined.CheckCircle else Icons.Outlined.Circle,
                    contentDescription = "",
                    tint = tintColor
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                Column {
                    Text(
                        text = item.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = textColor,
                        textDecoration = if (isDone) TextDecoration.LineThrough else TextDecoration.None,
                    )
                    Spacer(modifier = Modifier.height(SpaceSmall))
                    Text(
                        text = item.description,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = textColor
                    )
                }
            }
            Box(modifier = Modifier) {
                IconButton(onClick = {
                    showActionsMenu = true
                }) {
                    Icon(
                        imageVector = Icons.Default.MoreHoriz,
                        contentDescription = "",
                        tint = tintColor
                    )
                }

                DropdownMenu(
                    expanded = showActionsMenu,
                    offset = DpOffset(x = (0).dp, y = 0.dp),
                    onDismissRequest = {
                        showActionsMenu = false
                    }
                ) {
                    actions.forEach { action ->
                        DropdownMenuItem(
                            onClick = {
                                showActionsMenu = false
                                onActionSelected(action)
                            }
                        ) {
                            Text(text = stringResource(id = action.value))
                        }
                    }
                }
            }
        }

        val formatter = DateTimeFormatter.ofPattern("MMM dd, hh:mm")
        val time = DateUtil.secondsToLocalDateTime(item.startDate)

        Text(
            modifier = Modifier.padding(end = SpaceMedium, bottom = SpaceSmall),
            text = time.format(formatter),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = textColor
        )
    }
}
