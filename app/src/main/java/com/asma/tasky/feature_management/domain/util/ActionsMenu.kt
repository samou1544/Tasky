package com.asma.tasky.feature_management.domain.util

import androidx.annotation.StringRes
import com.asma.tasky.R

sealed class ActionsMenu(@StringRes val value: Int) {
    object Open : ActionsMenu(R.string.open)
    object Edit : ActionsMenu(R.string.edit)
    object Delete : ActionsMenu(R.string.delete)
}
