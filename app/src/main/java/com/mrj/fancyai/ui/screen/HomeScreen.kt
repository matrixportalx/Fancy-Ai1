package com.mrj.fancyai.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("FancyAI - Phase 1 Foundation")

        Button(
            onClick = { navController.navigate("characters") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Characters")
        }

        Button(
            onClick = { navController.navigate("settings") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Settings")
        }
    }
}
