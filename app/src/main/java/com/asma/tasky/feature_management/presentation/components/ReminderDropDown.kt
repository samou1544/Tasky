package com.asma.tasky.feature_management.presentation.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.asma.tasky.feature_management.domain.util.Reminder

@Composable
fun ReminderDropDown(expanded: Boolean, onDismiss: () -> (Unit), onSelected: (Reminder) -> (Unit)) {
    DropdownMenu(
        expanded = expanded,
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