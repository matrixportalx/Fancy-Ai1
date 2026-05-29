package com.mrj.fancyai.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mrj.fancyai.data.repository.SettingsRepository

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var llmProvider by mutableStateOf(settingsRepository.llmProvider)
        private set

    var modelPath by mutableStateOf(settingsRepository.modelPath)
        private set

    var contextSize by mutableStateOf(settingsRepository.contextSize.toString())
        private set

    var threadCount by mutableStateOf(settingsRepository.threadCount.toString())
        private set

    var temperature by mutableStateOf(settingsRepository.temperature.toString())
        private set

    var topK by mutableStateOf(settingsRepository.topK.toString())
        private set

    var topP by mutableStateOf(settingsRepository.topP.toString())
        private set

    var gpuLayers by mutableStateOf(settingsRepository.gpuLayers.toString())
        private set

    var apiKey by mutableStateOf(settingsRepository.apiKey)
        private set

    var customBackendUrl by mutableStateOf(settingsRepository.customBackendUrl)
        private set

    fun updateLlmProvider(value: String) {
        llmProvider = value
        settingsRepository.llmProvider = value
    }

    fun updateModelPath(value: String) {
        modelPath = value
        settingsRepository.modelPath = value
    }

    fun updateContextSize(value: String) {
        contextSize = value
        value.toIntOrNull()?.let { settingsRepository.contextSize = it }
    }

    fun updateThreadCount(value: String) {
        threadCount = value
        value.toIntOrNull()?.let { settingsRepository.threadCount = it }
    }

    fun updateTemperature(value: String) {
        temperature = value
        value.toFloatOrNull()?.let { settingsRepository.temperature = it }
    }

    fun updateTopK(value: String) {
        topK = value
        value.toIntOrNull()?.let { settingsRepository.topK = it }
    }

    fun updateTopP(value: String) {
        topP = value
        value.toFloatOrNull()?.let { settingsRepository.topP = it }
    }

    fun updateGpuLayers(value: String) {
        gpuLayers = value
        value.toIntOrNull()?.let { settingsRepository.gpuLayers = it }
    }

    fun updateApiKey(value: String) {
        apiKey = value
        settingsRepository.apiKey = value
    }

    fun updateCustomBackendUrl(value: String) {
        customBackendUrl = value
        settingsRepository.customBackendUrl = value
    }
}
