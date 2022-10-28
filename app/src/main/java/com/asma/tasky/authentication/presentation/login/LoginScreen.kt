package com.asma.tasky.authentication.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.asma.tasky.R
import com.asma.tasky.authentication.presentation.components.FormTextField
import com.asma.tasky.authentication.domain.util.AuthError
import com.asma.tasky.core.presentation.ui.theme.*
import com.asma.tasky.core.util.Screen


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {},
) {
    val emailState = viewModel.emailState.value
    val passwordState = viewModel.passwordState.value
    val state = viewModel.loginState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.padding(top = SpaceExtraLarge),
            text = "Welcome Back!",
            color = Color.White,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = SpaceExtraLarge
                )
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge)
                    .align(Alignment.Center),
            ) {

                FormTextField(
                    modifier = Modifier
                        .padding(top = SpaceExtraLarge),

                    backgroundColor = LightGray,
                    text = emailState.text,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EmailEntered(it))
                    },
                    keyboardType = KeyboardType.Email,
                    error = when (emailState.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    hint = stringResource(id = R.string.login_hint)
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                FormTextField(
                    text = passwordState.text,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.PasswordEntered(it))
                    },
                    hint = stringResource(id = R.string.password_hint),
                    keyboardType = KeyboardType.Password,
                    error = when (passwordState.error) {
                        is AuthError.FieldEmpty -> stringResource(id = R.string.error_field_empty)
                        else -> ""
                    },
                    isPasswordVisible = state.isPasswordVisible,
                    backgroundColor = LightGray,
                    onPasswordToggleClick = {
                        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                    }
                )
                Spacer(modifier = Modifier.height(SpaceLarge))
                Button(
                    onClick = { viewModel.onEvent(LoginEvent.Login) },
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black)
                ) {
                    Text(text = "LOGIN", color = Color.White)
                }

                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.dont_have_an_account))
                    append(" ")
                    val signUpText = stringResource(id = R.string.sign_up)
                    withStyle(
                        style = SpanStyle(
                            color = LightPurple
                        )
                    ) {
                        append(signUpText)
                    }
                },
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(bottom = SpaceExtraLarge)
                    .align(Alignment.BottomCenter)
                    .clickable {
                        onNavigate(Screen.RegisterScreen.route)
                    }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    LoginScreen()
}