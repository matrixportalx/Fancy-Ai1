package com.mrj.fancyai.ui.phone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun PhoneScreen(
    charId: String,
    navController: NavHostController,
    viewModel: PhoneViewModel
) {
    val isCallActive = viewModel.isCallActive
    val isListening = viewModel.isListening
    val userText = viewModel.userText
    val aiResponse = viewModel.aiResponse

    if (!isCallActive) {
        InactiveCallUI(
            charId = charId,
            onStartCall = { viewModel.startCall() },
            onNavigateBack = { navController.popBackStack() }
        )
    } else {
        ActiveCallUI(
            charId = charId,
            isListening = isListening,
            userText = userText,
            aiResponse = aiResponse,
            onStartListening = { viewModel.startListening() },
            onStopListening = { viewModel.stopListening() },
            onEndCall = { viewModel.endCall(); navController.popBackStack() }
        )
    }
}

@Composable
fun InactiveCallUI(
    charId: String,
    onStartCall: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text("📞", style = MaterialTheme.typography.displayMedium)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Voice Call",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                "Character ID: $charId",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onStartCall,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.Call, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start Call")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}

@Composable
fun ActiveCallUI(
    charId: String,
    isListening: Boolean,
    userText: String,
    aiResponse: String,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    onEndCall: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            "Active Call",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // AI Response display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp)
        ) {
            Column {
                Text(
                    "AI Response:",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = aiResponse.ifEmpty { "Listening..." },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // User input display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .padding(12.dp)
        ) {
            Column {
                Text(
                    "You said:",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = userText.ifEmpty { "Waiting for input..." },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = if (isListening) onStopListening else onStartListening,
                modifier = Modifier
                    .size(64.dp),
            ) {
                Text(if (isListening) "🎤" else "🎤", style = MaterialTheme.typography.headlineMedium)
            }

            Spacer(modifier = Modifier.width(32.dp))

            Button(
                onClick = onEndCall,
                modifier = Modifier
                    .size(64.dp),
            ) {
                Text("📞", style = MaterialTheme.typography.headlineMedium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = if (isListening) "🎤 Listening..." else "Tap mic to speak",
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
