package com.asma.tasky.feature_management.presentation.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.asma.tasky.feature_management.domain.util.Reminder

@Composable
fun ReminderDropDown(expanded: Boolean, onDismiss: () -> (Unit), onSelected: (Reminder) -> (Unit)) {
    DropdownMenu(
        expanded = expanded,
        offset = DpOffset(x = (50).dp, y = 10.dp),
        onDismissRequest = {
            onDismiss()
        }
    ) {
        val items = listOf(
            Reminder.TenMinutesBefore,
            Reminder.ThirtyMinutesBefore,
            Reminder.OneHourBefore,
            Reminder.SixHoursBefore,
            Reminder.OneDayBefore
        )
        items.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onDismiss()
                    onSelected(item)
                }
            ) {
                Text(text = stringResource(id = item.value))
            }
        }
    }
}