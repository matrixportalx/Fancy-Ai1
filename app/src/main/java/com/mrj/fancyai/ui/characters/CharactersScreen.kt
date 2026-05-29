package com.mrj.fancyai.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mrj.fancyai.data.db.entity.CharacterEntity

@Composable
fun CharactersScreen(
    navController: NavHostController,
    viewModel: CharacterViewModel
) {
    val characters by viewModel.characters.collectAsState(initial = emptyList())
    val selectedId = viewModel.selectedCharacterId
    val editingChar = viewModel.editingCharacter
    var showDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Text(
                "Characters",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(characters) { character ->
                    CharacterCard(
                        character = character,
                        isSelected = character.id == selectedId,
                        onSelect = {
                            viewModel.selectCharacter(character.id)
                            navController.navigate("chat/${character.id}")
                        },
                        onEdit = {
                            viewModel.startEdit(character)
                            showDialog = true
                        },
                        onDelete = { viewModel.deleteCharacter(character.id) }
                    )
                    Divider()
                }
            }
        }

        // FAB for new character
        FloatingActionButton(
            onClick = {
                viewModel.cancelEdit()
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "New Character")
        }
    }

    // Edit dialog
    if (showDialog) {
        CharacterEditDialog(
            character = editingChar,
            onSave = { name, handle, bio, persona ->
                viewModel.saveCharacter(name, handle, bio, persona)
                showDialog = false
            },
            onCancel = {
                viewModel.cancelEdit()
                showDialog = false
            }
        )
    }
}

@Composable
fun CharacterCard(
    character: CharacterEntity,
    isSelected: Boolean,
    onSelect: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onSelect)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar placeholder
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "@${character.handle}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = onEdit) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit")
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun CharacterEditDialog(
    character: CharacterEntity?,
    onSave: (String, String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(character?.name ?: "") }
    var handle by remember { mutableStateOf(character?.handle ?: "") }
    var bio by remember { mutableStateOf(character?.bio ?: "") }
    var persona by remember { mutableStateOf(character?.persona ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            if (character != null) "Edit Character" else "New Character",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = handle,
            onValueChange = { handle = it },
            label = { Text("Handle") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = persona,
            onValueChange = { persona = it },
            label = { Text("Persona") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 4
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.End
        ) {
            Button(onClick = onCancel) {
                Text("Cancel")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(onClick = { onSave(name, handle, bio, persona) }) {
                Text("Save")
            }
        }
    }
}
