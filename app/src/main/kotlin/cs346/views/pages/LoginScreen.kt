package cs346.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import cs346.controller.AuthController
import cs346.controller.DefaultButton
import cs346.views.theme.ExtendedTheme
import cs346.views.theme.PADDING_MEDIUM
import cs346.views.theme.PADDING_SMALL
import io.ktor.client.statement.*
import io.ktor.http.*

enum class SignInMode {
    Login, SignUp
}

@Composable
fun LoginScreen(onNavigate: () -> Unit) {
    var signInMode by remember { mutableStateOf(SignInMode.Login) }
    var loginError = remember { mutableStateOf(false) }
    var errorText = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var firstName = remember { mutableStateOf("") }
    var lastName = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = ExtendedTheme.colors.landingBackground)
    ) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Text(
                text = if (signInMode == SignInMode.Login) "Login" else "Sign Up",
                style = ExtendedTheme.typography.pageTitle,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    containerColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                label = { Text("Email", color = Color.White) },
                singleLine = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(PADDING_MEDIUM, PADDING_MEDIUM, PADDING_MEDIUM, PADDING_SMALL),
                textStyle = ExtendedTheme.typography.courseBody
            )

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    unfocusedIndicatorColor = Color.White,
                    containerColor = Color.Transparent,
                    cursorColor = Color.White
                ),
                label = { Text("Password", color = Color.White) },
                singleLine = true,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(PADDING_SMALL),
                textStyle = ExtendedTheme.typography.courseBody,
                visualTransformation = PasswordVisualTransformation()
            )

            if (signInMode == SignInMode.SignUp) {
                OutlinedTextField(
                    value = firstName.value,
                    onValueChange = { firstName.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        containerColor = Color.Transparent,
                        cursorColor = Color.White
                    ),
                    label = { Text("First Name", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(PADDING_SMALL),
                    textStyle = ExtendedTheme.typography.courseBody
                )
                OutlinedTextField(
                    value = lastName.value,
                    onValueChange = { lastName.value = it },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        unfocusedIndicatorColor = Color.White,
                        containerColor = Color.Transparent,
                        cursorColor = Color.White
                    ),
                    label = { Text("Last Name", color = Color.White) },
                    singleLine = true,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(PADDING_SMALL),
                    textStyle = ExtendedTheme.typography.courseBody
                )
            }

            if (loginError.value) {
                Text(
                    errorText.value,
                    color = Color.Red,
                    style = ExtendedTheme.typography.courseBody,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                DefaultButton(
                    text = if (signInMode == SignInMode.Login) "Login" else "Sign Up",
                    onClick = {
                        if (signInMode == SignInMode.Login) {
                            AuthController.callRequest {
                                val response = AuthController.signIn(email.value, password.value)
                                if (response?.status?.isSuccess() == false) {
                                    loginError.value = true
                                    errorText.value = "Invalid credentials"
                                } else {
                                    onNavigate()
                                }
                            }

                        } else {
                            AuthController.callRequest {
                                val response = AuthController.signUp(
                                    email.value,
                                    password.value,
                                    firstName.value,
                                    lastName.value
                                )
                                if (response?.status?.isSuccess() == false) {
                                    loginError.value = true
                                    errorText.value = response.bodyAsText()
                                } else {
                                    onNavigate()
                                }
                            }
                        }

                    },
                    Modifier.padding(PADDING_SMALL)
                )
                DefaultButton(
                    text = if (signInMode == SignInMode.Login) "Switch to Sign Up" else "Switch to Login",
                    onClick = {
                        loginError.value = false
                        signInMode = if (signInMode == SignInMode.Login) SignInMode.SignUp else SignInMode.Login
                    },
                    Modifier.padding(PADDING_SMALL)
                )
            }
        }

    }
}