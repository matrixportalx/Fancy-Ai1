package com.mrj.fancyai.domain.inference

import android.util.Log
import com.mrj.fancyai.LlamaInference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LlamaEngine @Inject constructor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    private val _tokenFlow = MutableSharedFlow<Pair<Int, String>>(extraBufferCapacity = 256)
    val tokenFlow: SharedFlow<Pair<Int, String>> = _tokenFlow

    private val _doneFlow = MutableSharedFlow<Int>(extraBufferCapacity = 64)
    val doneFlow: SharedFlow<Int> = _doneFlow

    private var nextCbId = 1

    init {
        LlamaInference.streamBridge = object : LlamaInference.StreamBridge {
            override fun onToken(cbId: Int, token: String) {
                Log.d("LlamaEngine", "Token[$cbId]: ${token.take(50)}")
                _tokenFlow.tryEmit(cbId to token)
            }

            override fun onDone(cbId: Int) {
                Log.d("LlamaEngine", "Done[$cbId]")
                _doneFlow.tryEmit(cbId)
            }
        }
    }

    /**
     * Stream inference results as a Flow<String> of accumulated text.
     * Replaces the old JS bridge with a native Kotlin coroutine flow.
     */
    suspend fun infer(
        prompt: String,
        maxTokens: Int,
        temperature: Float,
        topK: Int,
        topP: Float
    ): Flow<String> = channelFlow {
        val cbId = synchronized(this@LlamaEngine) {
            nextCbId++
        }

        var accumulated = ""

        // Collect tokens in background
        val tokenJob = kotlinx.coroutines.launch {
            tokenFlow.collect { (id, token) ->
                if (id == cbId) {
                    accumulated += token
                    send(accumulated)
                }
            }
        }

        try {
            // Run inference on dispatcher
            withContext(dispatcher) {
                LlamaInference.nativeInferenceStream(
                    prompt, maxTokens, cbId, temperature, topK, topP
                )
            }

            // Wait for done signal
            doneFlow.collect { id ->
                if (id == cbId) {
                    return@collect
                }
            }
        } finally {
            tokenJob.cancel()
        }
    }

    fun cancelInference() {
        LlamaInference.cancelInference()
    }
}
