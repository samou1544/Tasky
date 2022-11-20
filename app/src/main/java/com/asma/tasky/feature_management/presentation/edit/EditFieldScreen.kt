package com.asma.tasky.feature_management.presentation.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.SpaceLarge
import com.asma.tasky.core.presentation.ui.theme.SpaceMedium
import com.asma.tasky.core.util.Constants
import com.asma.tasky.feature_management.presentation.edit.components.CustomTopBar

@Composable
fun EditFieldScreen(
    viewModel: EditViewModel = hiltViewModel(),
    onClickSave: (String, String) -> Unit,
    onBack: () -> (Unit)
) {
    val text by viewModel.text.collectAsState()
    val key by viewModel.key.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        val title = when (key) {
            Constants.KEY_TITLE -> stringResource(id = R.string.edit_title)
            Constants.KEY_DESCRIPTION -> stringResource(id = R.string.edit_description)
            else -> key
        }
        CustomTopBar(
            title = title,
            onClickSave = {
                onClickSave(key, text.text)
            }, onClickBack = onBack
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = SpaceLarge, horizontal = SpaceMedium),
            value = text.text,
            textStyle = TextStyle(
                fontSize = if (key == Constants.KEY_TITLE) 26.sp else 16.sp,
                fontWeight = FontWeight.Normal
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            onValueChange = {
                viewModel.onEvent(EditEvent.TextEntered(it))
            }
        )
    }
}
