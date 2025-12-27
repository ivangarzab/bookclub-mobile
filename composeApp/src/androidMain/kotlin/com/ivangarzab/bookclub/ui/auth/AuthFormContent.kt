package com.ivangarzab.bookclub.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.presentation.viewmodels.auth.LoginNavigation
import com.ivangarzab.bookclub.presentation.viewmodels.auth.OAuthProvider
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.theme.signInDiscord
import com.ivangarzab.bookclub.theme.signInGoogle
import com.ivangarzab.bookclub.ui.components.SocialButton

@Composable
fun AuthFormContent(
    modifier: Modifier = Modifier,
    mode: AuthFormMode,
    errorMessage: String? = null,
    onEmailSignIn: (String, String) -> Unit,
    onOAuthSignIn: (OAuthProvider) -> Unit,
    onNavigate: (LoginNavigation) -> Unit,
) {
    //TODO: Should these be hoisted?
    var emailField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short,
            )
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    actionColor = MaterialTheme.colorScheme.error
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Welcome to your Kluvs",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Sign in to your account",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )

            Spacer(modifier = Modifier.height(24.dp))

            SocialButton(
                text = "Continue with Discord",
                icon = painterResource(R.drawable.ic_discord),
                iconSize = 20.dp,
                backgroundColor = signInDiscord,
                textColor = Color(0xFFFFFFFF),
                onClick = { onOAuthSignIn(OAuthProvider.DISCORD) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SocialButton(
                text = "Continue with Google",
                icon = painterResource(R.drawable.ic_google),
                iconSize = 40.dp,
                backgroundColor = signInGoogle,
                textColor = Color(0xFF1F1F1F),
                onClick = { onOAuthSignIn(OAuthProvider.GOOGLE) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextDivider(text = "or continue with email")

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = emailField,
                onValueChange = { emailField = it },
                singleLine = true,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Email, //TODO: Replace with custom icon
                        contentDescription = ""
                    )
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = passwordField,
                onValueChange = { passwordField = it },
                singleLine = true,
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, //TODO: Replace with custom icon
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions(
                    onGo = { onEmailSignIn(emailField, passwordField)}
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = ""
                    )
                },
            )

            TextButton (
                modifier = Modifier
                    .align(Alignment.End),
                onClick = { onNavigate(LoginNavigation.ForgetPassword) },
                content = {
                    Text(
                        text = "Forgot password?",
                        textAlign = TextAlign.Right,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                },
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = { onEmailSignIn(emailField, passwordField) }
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.background
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account?",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    modifier = Modifier
                        .clickable(
                            onClick = { onNavigate(LoginNavigation.SignUp) }
                        ),
                    text = "Sign up",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

enum class AuthFormMode { LOGIN, SIGNUP }

@PreviewLightDark
@Composable
fun Preview_LoginScreen() = KluvsTheme {
    AuthFormContent(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        mode = AuthFormMode.LOGIN,
        onOAuthSignIn = { _ -> },
        onEmailSignIn = { _, _ -> },
        onNavigate = { _ -> },
    )
}

@PreviewLightDark
@Composable
fun Preview_SignupScreen() = KluvsTheme {
    AuthFormContent(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        mode = AuthFormMode.SIGNUP,
        onOAuthSignIn = { _ -> },
        onEmailSignIn = { _, _ -> },
        onNavigate = { _ -> },
    )
}