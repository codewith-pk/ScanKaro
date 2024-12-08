package com.codewithpk.scankaro.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codewithpk.scankaro.data.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Insert
    fun insertAll(vararg users: User)
}
