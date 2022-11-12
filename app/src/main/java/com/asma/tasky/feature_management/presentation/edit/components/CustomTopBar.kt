package com.asma.tasky.feature_management.presentation.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.Green
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceSmall

@Composable
fun CustomTopBar(title: String, onClickSave: () -> (Unit), onClickBack: () -> (Unit)) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceSmall),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = stringResource(R.string.back_button_content_description)
                    )
                }
                Text(
                    text = stringResource(id = R.string.save),
                    fontWeight = FontWeight.SemiBold,
                    color = Green,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .clickable {
                            onClickSave()
                        }
                        .padding(horizontal = SpaceSmall)
                )
            }
            Text(text = title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
        }
        Divider(modifier = Modifier.padding(horizontal = SpaceLarge))
    }
}
