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
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivangarzab.bookclub.R
import com.ivangarzab.bookclub.presentation.viewmodels.auth.AuthState
import com.ivangarzab.bookclub.presentation.viewmodels.auth.AuthViewModel
import com.ivangarzab.bookclub.presentation.viewmodels.auth.LoginNavigation
import com.ivangarzab.bookclub.presentation.viewmodels.auth.OAuthProvider
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.theme.signInDiscord
import com.ivangarzab.bookclub.theme.signInGoogle
import com.ivangarzab.bookclub.ui.components.LoadingScreen
import com.ivangarzab.bookclub.ui.components.SocialButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = koinViewModel(),
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onNavigateToMain: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is AuthState.Loading -> LoadingScreen()
        is AuthState.Authenticated -> LaunchedEffect(Unit) {
            onNavigateToMain()
        }
        is AuthState.Unauthenticated,
        is AuthState.Error -> {
            AuthFormContent(
                modifier = modifier,
                mode = AuthFormMode.LOGIN,
                errorMessage = (state as? AuthState.Error)?.message,
                onEmailSignIn = viewModel::signIn,
                onOAuthSignIn = { provider ->
                    // TODO: Implement provider login
                },
                onNavigate = {
                    when (it) {
                        LoginNavigation.SignUp -> onNavigateToSignUp()
                        else -> onNavigateToForgotPassword()
                    }
                }
            )
        }
    }
}

@Composable
fun TextDivider(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Gray,
    thickness: Dp = 1.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = color,
            thickness = thickness
        )

        Text(
            text = text,
            color = color,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = color,
            thickness = thickness
        )
    }
}