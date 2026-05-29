package com.mrj.fancyai.ui.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrj.fancyai.data.db.entity.CharacterEntity
import com.mrj.fancyai.data.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

class CharacterViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters: Flow<List<CharacterEntity>> = characterRepository.getAllCharacters()

    var selectedCharacterId by mutableStateOf<String?>(null)
        private set

    var editingCharacter by mutableStateOf<CharacterEntity?>(null)
        private set

    fun selectCharacter(id: String) {
        selectedCharacterId = id
    }

    fun startEdit(character: CharacterEntity) {
        editingCharacter = character
    }

    fun cancelEdit() {
        editingCharacter = null
    }

    fun saveCharacter(
        name: String,
        handle: String,
        bio: String,
        persona: String,
        avatarRef: String? = null
    ) {
        viewModelScope.launch {
            val character = if (editingCharacter != null) {
                editingCharacter!!.copy(
                    name = name,
                    handle = handle,
                    bio = bio,
                    persona = persona,
                    avatarRef = avatarRef
                )
            } else {
                CharacterEntity(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    handle = handle,
                    bio = bio,
                    persona = persona,
                    avatarRef = avatarRef
                )
            }

            if (editingCharacter != null) {
                characterRepository.updateCharacter(character)
            } else {
                characterRepository.insertCharacter(character)
            }

            editingCharacter = null
        }
    }

    fun deleteCharacter(id: String) {
        viewModelScope.launch {
            characterRepository.deleteCharacter(id)
            if (selectedCharacterId == id) {
                selectedCharacterId = null
            }
        }
    }
}
