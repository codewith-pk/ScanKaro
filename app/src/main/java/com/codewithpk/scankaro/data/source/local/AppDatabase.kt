package com.codewithpk.scankaro.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codewithpk.scankaro.data.model.Tool
import com.codewithpk.scankaro.data.model.NewsArticle
import com.codewithpk.scankaro.data.model.User

@Database(entities = [Tool::class, NewsArticle::class, User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun toolDao(): ToolDao
    abstract fun newsDao(): NewsDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "scan_karo_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
