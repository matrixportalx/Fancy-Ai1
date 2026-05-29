package com.mrj.fancyai.data.repository

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SettingsRepository(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "fancy_ai_settings",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    var llmProvider: String
        get() = prefs.getString("llm_provider", "llama") ?: "llama"
        set(value) = prefs.edit().putString("llm_provider", value).apply()

    var modelPath: String
        get() = prefs.getString("model_path", "") ?: ""
        set(value) = prefs.edit().putString("model_path", value).apply()

    var contextSize: Int
        get() = prefs.getInt("context_size", 2048)
        set(value) = prefs.edit().putInt("context_size", value).apply()

    var threadCount: Int
        get() = prefs.getInt("thread_count", 4)
        set(value) = prefs.edit().putInt("thread_count", value).apply()

    var temperature: Float
        get() = prefs.getFloat("temperature", 0.7f)
        set(value) = prefs.edit().putFloat("temperature", value).apply()

    var topK: Int
        get() = prefs.getInt("top_k", 40)
        set(value) = prefs.edit().putInt("top_k", value).apply()

    var topP: Float
        get() = prefs.getFloat("top_p", 0.95f)
        set(value) = prefs.edit().putFloat("top_p", value).apply()

    var gpuLayers: Int
        get() = prefs.getInt("gpu_layers", 0)
        set(value) = prefs.edit().putInt("gpu_layers", value).apply()

    var apiKey: String
        get() = prefs.getString("api_key", "") ?: ""
        set(value) = prefs.edit().putString("api_key", value).apply()

    var customBackendUrl: String
        get() = prefs.getString("custom_backend_url", "") ?: ""
        set(value) = prefs.edit().putString("custom_backend_url", value).apply()
}
