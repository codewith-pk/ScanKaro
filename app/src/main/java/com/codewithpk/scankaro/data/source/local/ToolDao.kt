package com.codewithpk.scankaro.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codewithpk.scankaro.data.model.Tool

@Dao
interface ToolDao {
    @Query("SELECT * FROM tools")
    fun getAllTools(): List<Tool>

    @Insert
    fun insertAll(vararg tools: Tool)
}
