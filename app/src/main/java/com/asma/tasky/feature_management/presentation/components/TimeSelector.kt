package com.asma.tasky.feature_management.presentation.components

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
import com.asma.tasky.feature_management.domain.util.DateUtil
import java.time.LocalDateTime

@Composable
fun TimeSelector(
    label: String,
    startDateTime: LocalDateTime,
    editable: Boolean,
    onEditTime: () -> (Unit),
    onEditDate: () -> (Unit)
) {

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
            text = DateUtil.formatDate(startDateTime, "hh:mm"),
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
            text = DateUtil.formatDate(startDateTime, "MMM dd yyyy"),
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