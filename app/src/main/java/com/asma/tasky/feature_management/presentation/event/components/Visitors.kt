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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.domain.util.Util
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.domain.event.model.Attendee
import com.asma.tasky.feature_management.presentation.event.Attendees

@Preview
@Composable
fun Visitors() {
    val goingAttendees = listOf(
        Attendee(
            name = "Asma Smail",
            email = "",
            isCreator = false
        ),
        Attendee(
            name = "Mohamed Smail",
            email = "",
            isCreator = true
        ),
        Attendee(
            name = "Mona Smail",
            email = "",
            isCreator = false
        ),
    )

    val notGoingAttendees = listOf(
        Attendee(
            name = "Asma Smail",
            email = "",
            isCreator = false
        ),
        Attendee(
            name = "Mohamed Smail",
            email = "",
            isCreator = true
        ),
        Attendee(
            name = "Mona Smail",
            email = "",
            isCreator = false
        ),
    )


    val list = listOf(Attendees.All, Attendees.Going, Attendees.NotGoing)
    var selected by remember {
        mutableStateOf(list[2])
    }
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
                    .background(shape = RoundedCornerShape(5.dp), color = LightGray),
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
                            selected = it
                        },
                    textAlign = TextAlign.Center,
                    color = if (selected == it) Color.White else Color.Black

                )
            }

        }

        when (selected) {
            is Attendees.All -> {
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
                        AttendeesList(attendees = goingAttendees)
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
                        AttendeesList(attendees = notGoingAttendees)
                    }

            }
            is Attendees.Going -> {
                Column(
                    modifier = Modifier
                        .padding(horizontal = SpaceMedium)
                ) {
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    AttendeesList(attendees = goingAttendees)
                }
            }
            is Attendees.NotGoing -> {
                Column(
                    modifier = Modifier
                        .padding(horizontal = SpaceMedium)
                ) {
                    Spacer(modifier = Modifier.height(SpaceMedium))
                    AttendeesList(attendees = notGoingAttendees)
                }
            }
        }
    }
}

@Composable
fun AttendeesList(attendees: List<Attendee>) {
    Column {
        Spacer(modifier = Modifier.height(SpaceSmall))
        attendees.forEach { attendee ->
            AttendeesRow(attendee)
            Spacer(modifier = Modifier.height(SpaceSmall))
        }
    }


}

@Composable
fun AttendeesRow(attendee: Attendee, editable: Boolean = false) {

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
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Delete Attendee",
                        tint = Color.DarkGray
                    )
                }

    }

}