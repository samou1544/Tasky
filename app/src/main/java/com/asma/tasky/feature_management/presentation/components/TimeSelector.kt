package com.asma.tasky.feature_management.presentation.components

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

@Composable
fun TimeSelector (label:String, time:String, date:String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = SpaceMedium)
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
            text = time,
            modifier = Modifier.padding(start = SpaceSmall),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.width(80.dp))
        Text(
            text = date,
            modifier = Modifier.padding(start = SpaceSmall),
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp
        )
    }
}