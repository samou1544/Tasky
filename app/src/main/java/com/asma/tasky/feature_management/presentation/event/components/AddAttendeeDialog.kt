package com.asma.tasky.feature_management.presentation.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.asma.tasky.R
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.feature_authentication.domain.util.AuthError
import com.asma.tasky.feature_authentication.presentation.components.FormTextField
import com.asma.tasky.feature_management.presentation.event.util.AttendeeError

@Composable
fun AddAttendeeDialog(
    onCancel: () -> Unit,
    attendeeEmail: String,
    isEmailValid: Boolean?,
    error: com.asma.tasky.core.util.Error?,
    onAttendeeEntered: (String) -> Unit,
    onAttendeeAdded: (String) -> Unit,
    isLoading: Boolean
) {

    Dialog(onDismissRequest = onCancel) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(SpaceSmall))
                .padding(SpaceSmall)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(SpaceSmall),
                    text = "Add Attendee",
                    fontWeight = FontWeight.SemiBold
                )
                if (isLoading)
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.CenterVertically)
                    )
            }

            FormTextField(
                modifier = Modifier
                    .padding(top = SpaceExtraLarge),

                backgroundColor = LightGray,
                text = attendeeEmail,
                onValueChange = {
                    onAttendeeEntered(it)
                },
                keyboardType = KeyboardType.Email,
                error = when (error) {
                    is AuthError.InvalidEmail -> stringResource(id = R.string.error_invalid_email)
                    is AttendeeError.NoUserFound -> stringResource(id = R.string.user_not_found)
                    else -> ""
                },
                hint = stringResource(id = R.string.login_hint),
                isCheckMarkDisplayed = isEmailValid == true
            )



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge)
            ) {


                Button(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Gray,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(SpaceLarge))
                Button(
                    enabled = isEmailValid == true,
                    onClick = { onAttendeeAdded(attendeeEmail) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Green,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Add")
                }
            }
        }
    }
//    AlertDialog(
//        title = {
//            Column() {
//                Text("Add Attendee")
//                Spacer(modifier = Modifier.height(SpaceMedium))
//            }
//
//        },
//        text = {
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(SpaceSmall)
//            ) {
//                FormTextField(
//                    modifier = Modifier
//                        .padding(top = SpaceExtraLarge),
//
//                    backgroundColor = LightGray,
//                    text = attendeeEmail,
//                    onValueChange = {
//                        onAttendeeEntered(it)
//                    },
//                    keyboardType = KeyboardType.Email,
//                    error = when (error) {
//                        is AttendeeError.InvalidEmail -> stringResource(id = R.string.error_invalid_email)
//                        is AttendeeError.NoUserFound -> stringResource(id = R.string.user_not_found)
//                        else -> ""
//                    },
//                    hint = stringResource(id = R.string.login_hint),
//                    isCheckMarkDisplayed = isEmailValid == true
//                )
//
//                if (isLoading)
//                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
//
//            }
//        },
//        buttons = {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(SpaceLarge)
//            ) {
//
//
//                Button(
//                    onClick = onCancel,
//                    modifier = Modifier.weight(1f),
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = Color.Gray,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text(text = "Cancel")
//                }
//                Spacer(modifier = Modifier.width(SpaceLarge))
//                Button(
//                    enabled = isEmailValid == true,
//                    onClick = { onAttendeeAdded(attendeeEmail) },
//                    modifier = Modifier.weight(1f),
//                    colors = ButtonDefaults.buttonColors(
//                        backgroundColor = Green,
//                        contentColor = Color.White
//                    )
//                ) {
//                    Text(text = "Add")
//                }
//            }
//        },
//        onDismissRequest = { onCancel() }
//    )
}