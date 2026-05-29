package com.mrj.fancyai.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: String,
    val name: String,
    val handle: String = "",
    val bio: String = "",
    val persona: String = "",
    val avatarRef: String? = null,
    val enableRebbit: Boolean = true,
    val followerCount: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)
