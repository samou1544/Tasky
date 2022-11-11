package com.asma.tasky.feature_management.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.presentation.components.*

@Preview
@Composable
fun TaskScreen(editable: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SpaceMedium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Close",
                    tint = Color.White
                )
            }
            Text(
                text = "01 MARCH 2022",
                color = Color.White,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            if (editable) {
                Text(
                    text = "Save",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Normal
                )
            } else
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit, contentDescription = "Edit",
                        tint = Color.White
                    )
                }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = SpaceMedium, vertical = SpaceLarge)
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(color = Green, shape = RoundedCornerShape(2.dp))
                    )
                    Text(
                        text = "Task",
                        modifier = Modifier.padding(start = SpaceSmall),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = Color.DarkGray
                    )
                }

                //title
                Title(title = "Project X", editable = editable)
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium,
                        vertical = SpaceMedium
                    ), thickness = 1.dp, color = Color.LightGray
                )

                //description
                Description(description = "Weekly plan \nRole distribution", editable = editable)
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium,
                        vertical = SpaceMedium
                    ), thickness = 1.dp, color = Color.LightGray
                )

                //time
                TimeSelector(label = "At", time = "08:00", date = "Jul 21 2022")
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium,
                        vertical = SpaceMedium
                    ), thickness = 1.dp, color = Color.LightGray
                )

                //Reminder
                ReminderSelector()
                Divider(
                    modifier = Modifier.padding(
                        horizontal = SpaceMedium,
                        vertical = SpaceLarge
                    ), thickness = 1.dp, color = Color.LightGray
                )

                //Delete
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    DeleteText(text = "DELETE TASK")

                }

            }

        }
    }
}