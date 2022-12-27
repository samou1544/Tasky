package com.asma.tasky.feature_management.presentation.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.domain.util.UserUtil
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.domain.event.model.Attendee

@Composable
fun Visitors(

    onAddAttendee: () -> Unit,
    goingAttendees: List<Attendee>,
    notGoingAttendees: List<Attendee>,
    editable: Boolean,
    onRemoveAttendee: (Attendee) -> Unit
) {
    //todo
}

@Composable
fun AttendeesList(
    attendees: List<Attendee>,
    editable: Boolean,
    onRemoveAttendee: (Attendee) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(SpaceSmall))
        attendees.forEach { attendee ->
            AttendeesRow(attendee, editable, onRemoveAttendee = onRemoveAttendee)
            Spacer(modifier = Modifier.height(SpaceSmall))
        }
    }


}

@Composable
fun AttendeesRow(attendee: Attendee, editable: Boolean, onRemoveAttendee: (Attendee) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LightGray, shape = RoundedCornerShape(10.dp))
            .padding(SpaceSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(color = DarkGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = UserUtil.getInitials(attendee.fullName),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.White
                )

            }

            Spacer(modifier = Modifier.width(SpaceMedium))

            Text(
                text = attendee.fullName,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        if (attendee.isCreator)
            Text(
                text = "creator",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightBlue
            )
        else
            if (editable)
                IconButton(onClick = {
                    onRemoveAttendee(attendee)
                }) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete Attendee",
                        tint = Color.DarkGray
                    )
                }

    }

}