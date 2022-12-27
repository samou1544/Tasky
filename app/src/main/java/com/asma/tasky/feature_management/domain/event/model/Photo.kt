package com.asma.tasky.feature_management.domain.event.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val key: String,
    val url: String
) : Parcelable
