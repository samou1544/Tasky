package com.asma.tasky.core.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.asma.tasky.R

private val Inter = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_bold, weight = FontWeight.Bold),
    Font(R.font.inter_light, weight = FontWeight.Light),
    Font(R.font.inter_black, weight = FontWeight.Black),
    Font(R.font.inter_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.inter_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.inter_medium, weight = FontWeight.Medium),
    Font(R.font.inter_semibold, weight = FontWeight.SemiBold),
    Font(R.font.inter_thin, weight = FontWeight.Thin),
    )

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = Inter
)