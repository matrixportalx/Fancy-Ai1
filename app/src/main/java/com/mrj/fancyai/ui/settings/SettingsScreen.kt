package com.mrj.fancyai.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: SettingsViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // LLM Provider
        Text(
            "LLM Provider",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = viewModel.llmProvider,
            onValueChange = { viewModel.updateLlmProvider(it) },
            label = { Text("Provider (llama/openrouter/deepinfra/custom)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        // Local LLM settings
        Text(
            "Local LLM (On-Device)",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = viewModel.modelPath,
            onValueChange = { viewModel.updateModelPath(it) },
            label = { Text("Model Path (e.g., /sdcard/models/model.gguf)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.contextSize,
            onValueChange = { viewModel.updateContextSize(it) },
            label = { Text("Context Size") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.threadCount,
            onValueChange = { viewModel.updateThreadCount(it) },
            label = { Text("Thread Count") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.gpuLayers,
            onValueChange = { viewModel.updateGpuLayers(it) },
            label = { Text("GPU Layers (NPU offload)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        // Inference parameters
        Text(
            "Inference Parameters",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = viewModel.temperature,
            onValueChange = { viewModel.updateTemperature(it) },
            label = { Text("Temperature (0.0 - 2.0)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.topK,
            onValueChange = { viewModel.updateTopK(it) },
            label = { Text("Top K") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.topP,
            onValueChange = { viewModel.updateTopP(it) },
            label = { Text("Top P (0.0 - 1.0)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Divider()

        // Cloud provider settings
        Text(
            "Cloud Providers",
            style = MaterialTheme.typography.titleMedium
        )

        OutlinedTextField(
            value = viewModel.apiKey,
            onValueChange = { viewModel.updateApiKey(it) },
            label = { Text("API Key") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = viewModel.customBackendUrl,
            onValueChange = { viewModel.updateCustomBackendUrl(it) },
            label = { Text("Custom Backend URL") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}
