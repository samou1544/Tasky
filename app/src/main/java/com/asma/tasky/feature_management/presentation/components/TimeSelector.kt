package com.asma.tasky.feature_management.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimeSelector(
    label: String,
    startDateTime: LocalDateTime,
    editable: Boolean,
    onEditTime: () -> (Unit),
    onEditDate: () -> (Unit)
) {
    val hourFormatter = DateTimeFormatter.ofPattern("hh:mm")
    val dayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(SpaceMedium)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(start = SpaceSmall),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(50.dp))
        Text(
            text = startDateTime
                .format(hourFormatter),
            modifier = Modifier
                .clickable(enabled = editable) {
                    onEditTime()
                }
                .padding(start = SpaceSmall),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(80.dp))
        Text(
            text = startDateTime
                .format(dayFormatter),
            modifier = Modifier
                .clickable(enabled = editable) {
                    onEditDate()
                }
                .padding(start = SpaceSmall),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
    }
}
