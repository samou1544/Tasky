package com.asma.tasky.feature_management.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.core.presentation.ui.theme.Yellow
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.*

@Composable
fun DayItem(
    modifier: Modifier,
    selected: Boolean = false,
    day: LocalDateTime,
    onCLick: (LocalDateTime) -> Unit
) {
    Column(
        modifier = modifier.then(
            Modifier
                .clip(CircleShape)
                .background(
                    color = if (selected) Yellow else Color.Transparent
                )
                .clickable {
                    onCLick(day)
                }
                .padding(vertical = SpaceSmall)
        ),
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
