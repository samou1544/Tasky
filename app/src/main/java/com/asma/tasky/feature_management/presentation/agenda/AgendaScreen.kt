package com.asma.tasky.feature_management.presentation.agenda

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asma.tasky.R
import com.asma.tasky.core.domain.util.Util
import com.asma.tasky.core.presentation.ui.theme.LightGray
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import com.asma.tasky.feature_management.domain.AgendaItem
import com.asma.tasky.feature_management.presentation.agenda.components.AgendaListItem
import com.asma.tasky.feature_management.presentation.agenda.components.DayItem
import com.asma.tasky.feature_management.presentation.agenda.components.Needle
import java.time.LocalDate
import java.util.*

@Preview
@Composable
fun AgendaScreen(
    viewModel: AgendaViewModel = hiltViewModel(),
    onClick: (AgendaItem) -> Unit = {}
) {
    val state by viewModel.agendaState.collectAsState()

    val pastItems by remember {
        derivedStateOf {
            state.items.filter {
                it.startDate < System.currentTimeMillis() / 1000
            }
        }
    }

    val futureItems by remember {
        derivedStateOf {
            state.items.filter {
                it.startDate >= System.currentTimeMillis() / 1000
            }
        }
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
                    Text(
                        text = state.selectedDate.month.name.capitalize(Locale.US),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = stringResource(R.string.drop_down_arrow_content_description),
                        tint = Color.White
                    )
                }
            }

            if (state.userName.isNotEmpty())
                Text(
                    text = Util.getInitials(state.userName),
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
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SpaceLarge)
                ) {
                    repeat(7) {
                        DayItem(
                            selected = it == 0,
                            state.selectedDate.plusDays(it.toLong()),
                            onCLick = {
                                // TODO update selected day
                            }
                        )
                    }
                }

                Text(
                    text = if (state.selectedDate.toLocalDate() == LocalDate.now()) stringResource(R.string.today)
                    else state.selectedDate.dayOfWeek.name,
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
