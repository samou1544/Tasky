package com.asma.tasky.feature_management.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall

@Composable
fun Description(description: String, editable: Boolean = false) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = SpaceMedium)
            .fillMaxWidth()
    ) {
        Text(
            text = description,
            modifier = Modifier.padding(start = SpaceSmall),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        if (editable)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.KeyboardArrowRight,
                    contentDescription = "reminder icon"
                )
            }
    }
}