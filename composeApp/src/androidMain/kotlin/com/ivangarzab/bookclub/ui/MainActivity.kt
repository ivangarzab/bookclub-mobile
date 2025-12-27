package com.ivangarzab.bookclub.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ivangarzab.bookclub.theme.KluvsTheme
import com.ivangarzab.bookclub.ui.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            KluvsTheme {
                val navController = rememberNavController()
                MainNavHost(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.background),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = NavDestinations.LOGIN
    ) {
        composable(NavDestinations.LOGIN) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(NavDestinations.SIGNUP)
                },
                onNavigateToForgotPassword = {
                    navController.navigate(NavDestinations.FORGOT_PASSWORD)
                },
                onNavigateToMain = {
                    navController.navigate(NavDestinations.MAIN) {
                        popUpTo(NavDestinations.LOGIN) { inclusive = true }
                    }
                },
            )
        }
        composable(NavDestinations.SIGNUP) {
            //TODO
        }
        composable(NavDestinations.FORGOT_PASSWORD) {
            //TODO
        }
        composable(NavDestinations.MAIN) {
            MainScreen()
        }
    }
}

object NavDestinations {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
    const val FORGOT_PASSWORD = "forgot_password"
    const val MAIN = "main"
}