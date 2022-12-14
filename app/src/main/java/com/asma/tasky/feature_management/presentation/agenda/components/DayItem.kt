package com.asma.tasky.feature_management.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.core.presentation.ui.theme.Yellow
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

@Composable
fun DayItem(selected: Boolean = false, day: LocalDateTime, onCLick: (LocalDateTime) -> Unit) {
    Column(
        modifier = Modifier
            .clip(CircleShape)
            .background(
                color = if (selected) Yellow else Color.Transparent
            )
            .clickable {
                onCLick(day)
            }
            .padding(vertical = SpaceSmall, horizontal = SpaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day.dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.ENGLISH),
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(SpaceSmall))
        Text(
            text = "${day.dayOfMonth}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
