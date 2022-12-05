package com.asma.tasky.feature_management.presentation.agenda.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall

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
