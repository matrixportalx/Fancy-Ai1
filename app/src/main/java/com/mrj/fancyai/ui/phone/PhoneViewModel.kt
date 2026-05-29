package com.mrj.fancyai.ui.phone

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrj.fancyai.data.repository.ChatRepository
import com.mrj.fancyai.domain.inference.LlamaEngine
import com.mrj.fancyai.service.VoiceService
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.UUID

class PhoneViewModel(
    private val charId: String,
    private val chatRepository: ChatRepository,
    private val llamaEngine: LlamaEngine,
    private val voiceService: VoiceService
) : ViewModel() {

    var isCallActive by mutableStateOf(false)
        private set

    var isListening by mutableStateOf(false)
        private set

    var userText by mutableStateOf("")
        private set

    var aiResponse by mutableStateOf("")
        private set

    var callDuration by mutableStateOf(0)
        private set

    private var callStartTime = 0L

    fun startCall() {
        isCallActive = true
        callStartTime = System.currentTimeMillis()

        viewModelScope.launch {
            voiceService.isListening.collect { listening ->
                isListening = listening
            }
        }

        viewModelScope.launch {
            voiceService.recognizedText.collect { text ->
                userText = text
                processUserInput(text)
            }
        }
    }

    fun endCall() {
        isCallActive = false
        voiceService.stopListening()
        voiceService.stopSpeaking()
    }

    fun startListening() {
        voiceService.startListening()
    }

    fun stopListening() {
        voiceService.stopListening()
    }

    private fun processUserInput(text: String) {
        viewModelScope.launch {
            if (text.isBlank()) return@launch

            isListening = false
            aiResponse = ""

            try {
                val cbId = llamaEngine.getNextCbId()
                var accumulated = ""

                llamaEngine.tokenFlow.collect { (id, token) ->
                    if (id == cbId) {
                        accumulated += token
                        aiResponse = accumulated
                    }
                }

                // Speak the response
                voiceService.speak(accumulated)

                // Save to chat history
                val userMsg = com.mrj.fancyai.data.db.entity.MessageEntity(
                    id = UUID.randomUUID().toString(),
                    charId = charId,
                    sender = "user",
                    text = text,
                    type = "text",
                    timestamp = System.currentTimeMillis()
                )
                val aiMsg = com.mrj.fancyai.data.db.entity.MessageEntity(
                    id = UUID.randomUUID().toString(),
                    charId = charId,
                    sender = "ai",
                    text = accumulated,
                    type = "text",
                    timestamp = System.currentTimeMillis()
                )

                chatRepository.insertMessage(userMsg)
                chatRepository.insertMessage(aiMsg)

                // Ready for next input
                userText = ""
            } catch (e: Exception) {
                aiResponse = "Error: ${e.message}"
            }
        }
    }

    override fun onCleared() {
        voiceService.stopListening()
        voiceService.stopSpeaking()
        super.onCleared()
    }
}
