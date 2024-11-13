package com.example.stylish.navigationroot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stylish.ui.presentation.features.login.LoginRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    val context = LocalContext.current
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = Screen.Login.name,
    ) {
        composable(Screen.Splash.name) {
            SplashRoot(
                onAnimationFinished = {
                    navController.navigate(Screen.Login.name)
                }
            )
        }

        composable(Screen.Login.name) {
            LoginRoot(
                onSuccessfulLogin = {

                }
            )
        }
    }
}














