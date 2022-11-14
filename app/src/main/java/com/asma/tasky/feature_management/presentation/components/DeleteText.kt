package com.asma.tasky.feature_management.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.SpaceExtraLarge

@Composable
fun DeleteText(text: String, onDelete: () -> (Unit)) {
    Text(
        modifier = Modifier
            .padding(vertical = SpaceExtraLarge)
            .clickable {
                onDelete()
            },
        text = text,
        color = Color.LightGray,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    )
}
