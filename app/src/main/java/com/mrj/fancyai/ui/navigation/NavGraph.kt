package com.mrj.fancyai.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mrj.fancyai.ui.screen.HomeScreen
import com.mrj.fancyai.ui.screen.CharactersScreen
import com.mrj.fancyai.ui.screen.ChatScreen
import com.mrj.fancyai.ui.screen.SettingsScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("characters") {
            CharactersScreen(navController = navController)
        }
        composable("chat/{charId}") { backStackEntry ->
            val charId = backStackEntry.arguments?.getString("charId") ?: ""
            ChatScreen(charId = charId, navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}
