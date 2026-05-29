package com.mrj.fancyai.domain.inference

import android.util.Log
import com.mrj.fancyai.LlamaInference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class LlamaEngine {
    private val _tokenFlow = MutableSharedFlow<Pair<Int, String>>(extraBufferCapacity = 256)
    val tokenFlow: Flow<Pair<Int, String>> = _tokenFlow

    private val _doneFlow = MutableSharedFlow<Int>(extraBufferCapacity = 64)
    val doneFlow: Flow<Int> = _doneFlow

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

    fun getNextCbId(): Int = synchronized(this) { nextCbId++ }

    fun cancelInference() {
        LlamaInference.cancelInference()
    }
}
