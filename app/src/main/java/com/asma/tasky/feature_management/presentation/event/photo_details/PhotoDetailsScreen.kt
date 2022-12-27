package com.asma.tasky.feature_management.presentation.event.photo_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import okio.ByteString.Companion.decodeBase64
import java.nio.charset.Charset

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PhotoDetailsScreen(
    imageLoader: ImageLoader, imageUrl: String, onClose: () -> (Unit), onDelete: (String) -> (Unit)
) {
    val decodedImageUrl = remember {
        imageUrl.decodeBase64()?.string(Charset.defaultCharset())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }

            Text(
                text = stringResource(R.string.Photo),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            IconButton(onClick = {
                onDelete(decodedImageUrl?:"")
            }) {
                Icon(
                    imageVector = Icons.Outlined.DeleteOutline,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }

        Image(modifier = Modifier
            .fillMaxWidth()
            .padding(SpaceLarge),
            painter = rememberImagePainter(data = decodedImageUrl,
                imageLoader = imageLoader,
                builder = {
                    placeholder(R.drawable.ic_launcher_background)
                    error(R.drawable.ic_launcher_background)
                }),
            contentDescription = "",
            contentScale = ContentScale.FillWidth)
    }
}
