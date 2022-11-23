package com.asma.tasky.feature_management.presentation.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_management.domain.AgendaItem
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Preview
@Composable
fun AgendaScreen(
    viewModel: AgendaViewModel = hiltViewModel(),
    onClick: (AgendaItem) -> Unit = {}
) {
    val state by viewModel.agendaState.collectAsState()
    val pastItems = state.items.filter {
        it.startDate!! < System.currentTimeMillis() / 1000
    }
    val futureItems = state.items.filter {
        it.startDate!! >= System.currentTimeMillis() / 1000
    }

    LaunchedEffect(key1 = true) {
        viewModel.getTasks()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = SpaceMedium, vertical = SpaceSmall)
        ) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "NOVEMBER", color = Color.White, fontSize = 18.sp)
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "drop down arrow",
                        tint = Color.White
                    )
                }
            }

            Text(
                text = "AS",
                modifier = Modifier
                    .padding(SpaceSmall)
                    .background(color = LightGray, shape = CircleShape)
                    .padding(SpaceSmall)
                    .clickable {

                    }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()

                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceLarge)
                ) {
                    repeat(7) {
                        val days = arrayOf(
                            "Monday",
                            "Tuesday",
                            "Wednesday",
                            "Thursday",
                            "Friday",
                            "Saturday",
                            "Sunday"
                        )
                        DayItem(selected = it == 0, days[it], it)
                    }
                }

                Text(
                    text = "Today",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = SpaceLarge)
                )

                pastItems.forEach { item ->
                    AgendaListItem(item = item) {

                    }

                }
                Needle()
                futureItems.forEach { item ->
                    AgendaListItem(item = item) {
                        onClick(item)
                    }

                }
            }

        }
    }
}


@Composable
fun DayItem(selected: Boolean = false, day: String, dayNumber: Int) {
    Column(
        modifier = Modifier

            .background(
                color = if (selected) Yellow else Color.Transparent,
                shape = CircleShape
            )
            .padding(vertical = SpaceSmall, horizontal = SpaceMedium)
    ) {
        Text(
            text = "${day.first()}",
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(SpaceSmall))
        Text(
            text = "$dayNumber",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

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
            val isDone = (item is AgendaItem.Task && item.isDone) || (item is AgendaItem.Event && item.isDone)
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    modifier = Modifier.padding(top = 2.dp),
                    //todo check if it's done
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
                        text = item.description?: "",
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
        val time = LocalDateTime.ofEpochSecond(item.startDate!!, 0, ZoneOffset.UTC)

        Text(
            modifier = Modifier.padding(end = SpaceMedium, bottom = SpaceSmall),
            text = time.format(formatter),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = textColor
        )

    }
}

@Preview
@Composable
fun Needle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = Color.Black, shape = CircleShape)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(color = Color.Black, shape = RectangleShape)
        )

    }
}
