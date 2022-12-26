package com.asma.tasky.feature_management.presentation.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.domain.util.Util
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.presentation.event.AttendeesStatus

@Composable
fun Visitors(
    selected: AttendeesStatus,
    onAddAttendee: () -> Unit,
    onChangeStatus: (AttendeesStatus) -> Unit,
    goingAttendees: List<Attendee>,
    notGoingAttendees: List<Attendee>,
    editable: Boolean,
    onRemoveAttendee: (Attendee) -> Unit
) {
    val list = listOf(AttendeesStatus.All, AttendeesStatus.Going, AttendeesStatus.NotGoing)

    Column(
        modifier = Modifier
            .background(color = Color.White)
            .padding(vertical = SpaceMedium)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = SpaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "Visitors", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(SpaceLarge))
            Box(
                modifier = Modifier
                    .size(35.dp)
                    .background(shape = RoundedCornerShape(5.dp), color = LightGray)
                    .clickable {
                        onAddAttendee()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(4.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = "",
                    tint = DarkGray
                )
            }
//


        }
        Spacer(modifier = Modifier.height(SpaceLarge))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            list.forEach {
                Text(
                    text = it.value,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = SpaceMedium)
                        .background(
                            color = if (selected == it) Color.Black else LightGray,
                            shape = CircleShape
                        )
                        .padding(vertical = SpaceSmall)
                        .clickable {
                            onChangeStatus(it)
                        },
                    textAlign = TextAlign.Center,
                    color = if (selected == it) Color.White else Color.Black

                )
            }

        }

        when (selected) {
            is AttendeesStatus.All -> {
                if (goingAttendees.isNotEmpty())
                    Column(
                        modifier = Modifier
                            .padding(horizontal = SpaceMedium)
                    ) {
                        Spacer(modifier = Modifier.height(SpaceMedium))
                        Text(
                            text = "Going",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        AttendeesList(
                            attendees = goingAttendees,
                            editable = editable,
                            onRemoveAttendee = onRemoveAttendee
                        )
                    }
                if (notGoingAttendees.isNotEmpty())
                    Column(
                        modifier = Modifier
                            .padding(horizontal = SpaceMedium)
                    ) {
                        Spacer(modifier = Modifier.height(SpaceMedium))
                        Text(
                            text = "Not going",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        AttendeesList(
                            attendees = notGoingAttendees,
                            editable = editable,
                            onRemoveAttendee = onRemoveAttendee
                        )
                    }

            }
            is AttendeesStatus.Going -> {
                Column(
                    modifier = Modifier
                        .padding(horizontal = SpaceMedium)
                ) {
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    AttendeesList(
                        attendees = goingAttendees,
                        editable = editable,
                        onRemoveAttendee = onRemoveAttendee
                    )
                }
            }
            is AttendeesStatus.NotGoing -> {
                Column(
                    modifier = Modifier
                        .padding(horizontal = SpaceMedium)
                ) {
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    AttendeesList(
                        attendees = notGoingAttendees,
                        editable = editable,
                        onRemoveAttendee = onRemoveAttendee
                    )
                }
            }
        }
    }
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
                    text = Util.getInitials(attendee.name),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.White
                )

            }

            Spacer(modifier = Modifier.width(SpaceMedium))

            Text(
                text = attendee.name,
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