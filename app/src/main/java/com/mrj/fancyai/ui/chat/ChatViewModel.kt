package com.mrj.fancyai.ui.chat

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrj.fancyai.data.db.entity.MessageEntity
import com.mrj.fancyai.data.repository.ChatRepository
import com.mrj.fancyai.data.repository.SettingsRepository
import com.mrj.fancyai.domain.inference.LlamaEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(
    private val charId: String,
    private val chatRepository: ChatRepository,
    private val settingsRepository: SettingsRepository,
    private val llamaEngine: LlamaEngine
) : ViewModel() {

    val messages: Flow<List<MessageEntity>> = chatRepository.getMessagesForCharacter(charId)

    var streamingText by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun sendMessage(userText: String) {
        if (userText.isBlank() || isLoading) return

        viewModelScope.launch {
            // Save user message
            val userMsg = MessageEntity(
                id = UUID.randomUUID().toString(),
                charId = charId,
                sender = "user",
                text = userText,
                type = "text",
                timestamp = System.currentTimeMillis()
            )
            chatRepository.insertMessage(userMsg)

            isLoading = true
            streamingText = ""

            try {
                val cbId = llamaEngine.getNextCbId()
                var accumulated = ""

                // Collect tokens from LlamaEngine
                llamaEngine.tokenFlow.collect { (id, token) ->
                    if (id == cbId) {
                        accumulated += token
                        streamingText = accumulated
                    }
                }

                // When done, save AI response
                if (accumulated.isNotEmpty()) {
                    val aiMsg = MessageEntity(
                        id = UUID.randomUUID().toString(),
                        charId = charId,
                        sender = "ai",
                        text = accumulated,
                        type = "text",
                        timestamp = System.currentTimeMillis()
                    )
                    chatRepository.insertMessage(aiMsg)
                }

                streamingText = ""
            } finally {
                isLoading = false
            }
        }
    }

    fun clearChat() {
        viewModelScope.launch {
            chatRepository.clearCharacterChat(charId)
        }
    }
}
