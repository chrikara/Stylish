package com.example.stylish.navigationroot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.core.presentation.components.Screen
import com.example.login.presentation.LoginRoot
import com.example.products.presentation.details.ProductDetailsRoot
import com.example.products.presentation.main.ProductsRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        navController = navController,
        startDestination = Screen.Splash.toString(),
    ) {
        composable(Screen.Splash.toString()) {
            SplashRoot(
                onAnimationFinished = { isLoggedIn ->
                    navController.popBackStack()
                    if (isLoggedIn)
                        navController.navigate(Screen.Products.toString())
                    else
                        navController.navigate(Screen.Login.toString())
                }
            )
        }

        composable(Screen.Login.toString()) {
            LoginRoot(
                onSuccessfulLogin = {
                    navController.popBackStack()
                    navController.navigate(Screen.Products.toString())
                }
            )
        }

        composable(Screen.Products.toString()) {
            ProductsRoot(
                onProductClicked = {
                    navController.navigate(
                        route = Screen.Edit(id = it.id)
                    )
                }
            )
        }

        composable<Screen.Edit> {
            ProductDetailsRoot(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}














