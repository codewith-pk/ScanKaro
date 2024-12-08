package com.codewithpk.scankaro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tools")
data class Tool(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String
)
