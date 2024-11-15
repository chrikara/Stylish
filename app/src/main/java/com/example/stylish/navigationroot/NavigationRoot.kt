package com.example.stylish.navigationroot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.login.presentation.LoginRoot
import com.example.products.presentation.ProductsRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = Screen.Products.name,
    ) {
        composable(Screen.Splash.name) {
            SplashRoot(
                onAnimationFinished = { isLoggedIn ->
                    navController.popBackStack()
                    if (isLoggedIn)
                        navController.navigate(Screen.Products.name)
                    else
                        navController.navigate(Screen.Login.name)
                }
            )
        }

        composable(Screen.Login.name) {
            LoginRoot(
                onSuccessfulLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.Products.name)
                }
            )
        }

        composable(Screen.Products.name) {
            ProductsRoot()
        }
    }
}














