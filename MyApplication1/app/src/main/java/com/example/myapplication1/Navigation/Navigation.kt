package com.example.myapplication1.Navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication1.Screens.HomeScreen
import com.example.myapplication1.Screens.LoginScreen
import com.example.myapplication1.Screens.RegisterScreen
import androidx.navigation.compose.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.remember
import com.example.myapplication1.Data.AppDatabase

@Composable
fun AppNavigation() {

    val context = LocalContext.current
    val db = remember {
        androidx.room.Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
    val userDao = db.userDao()

    //navigation
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {

            LoginScreen(
                userDao = userDao,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {

            RegisterScreen(
                userDao = userDao,
                onComplete = {
                    navController.popBackStack()
                }
            )
        }

        composable("home") {
            HomeScreen()
        }
    }
}