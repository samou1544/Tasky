package com.asma.tasky.feature_management.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.domain.AgendaItem
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun AgendaListItem(item: AgendaItem, onClick: (AgendaItem) -> (Unit)) {
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

    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceSmall)
            .background(
                color = backgroundColor, shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick(item) }
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
                    modifier = Modifier.padding(top = 2.dp),
                    // todo check if it's done
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

            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "",
                    tint = tintColor
                )
            }
        }

        val formatter = DateTimeFormatter.ofPattern("MMM dd, hh:mm")
        val time = LocalDateTime.ofEpochSecond(item.startDate, 0, ZoneOffset.UTC)

        Text(
            modifier = Modifier.padding(end = SpaceMedium, bottom = SpaceSmall),
            text = time.format(formatter),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = textColor
        )
    }
}
