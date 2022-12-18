package com.asma.tasky.feature_management.presentation.edit

import com.asma.tasky.core.util.Constants

sealed class EditableField(val key: String) {
    object Title : EditableField(Constants.PARAM_TITLE)
    object Description : EditableField(Constants.KEY_DESCRIPTION)
}
