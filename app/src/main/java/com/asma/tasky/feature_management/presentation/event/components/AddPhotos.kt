package com.asma.tasky.feature_management.presentation.event.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun AddPhotos(
    photos: List<Any> = emptyList(),
    imageLoader: ImageLoader,
    onClick: () -> (Unit),
    onClickPhoto: (String) -> (Unit)
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LightGray),
        contentAlignment = Alignment.Center
    ) {
        if (photos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick()
                    }
            ) {
                Row(
                    modifier = Modifier.padding(SpaceExtraLarge),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add photo icon",
                        tint = DarkGray
                    )
                    Spacer(modifier = Modifier.width(SpaceSmall))
                    Text(
                        text = "Add Photos",
                        color = DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            Column {
                Text(
                    modifier = Modifier
                        .padding(SpaceMedium),
                    text = "Photos",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                ) {
                    photos.forEach {
                        Spacer(modifier = Modifier.width(SpaceMedium))
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(shape = RoundedCornerShape(5.dp))
                                .border(
                                    width = 1.dp,
                                    color = DarkGray,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .clickable {
                                    onClickPhoto(it.toString())
                                },
                            painter = rememberImagePainter(
                                data = it,
                                imageLoader = imageLoader,
                                builder = {
                                    placeholder(R.drawable.ic_launcher_background)
                                    error(R.drawable.ic_launcher_background)
                                }
                            ),
                            contentDescription = "",
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(SpaceMedium))
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .border(
                                width = 1.dp,
                                color = DarkGray,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .clickable {
                                onClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add photo icon",
                            tint = DarkGray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(SpaceMedium))
            }
        }
    }
}
