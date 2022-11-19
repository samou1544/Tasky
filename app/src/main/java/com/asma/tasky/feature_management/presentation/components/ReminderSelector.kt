package com.asma.tasky.feature_management.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.LightGray
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.feature_management.domain.util.Reminder

@Composable
fun ReminderSelector(editable: Boolean, reminder: Reminder, onClick: () -> (Unit)) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = editable, onClick = onClick)
            .padding(SpaceMedium)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "reminder icon",
                modifier = Modifier
                    .background(
                        color = LightGray,
                        shape = RoundedCornerShape(2.dp)
                    )
                    .padding(2.dp),
                tint = Color.DarkGray
            )
            Text(
                text = stringResource(id = reminder.value),
                modifier = Modifier.padding(start = SpaceMedium),
                fontWeight = FontWeight.Normal,
                fontSize = 18.sp
            )
        }

        if (editable)
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "reminder icon"
            )
    }
}
