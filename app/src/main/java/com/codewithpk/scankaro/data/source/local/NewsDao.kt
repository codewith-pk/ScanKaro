package com.codewithpk.scankaro.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.codewithpk.scankaro.data.model.NewsArticle

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAllNews(): List<NewsArticle>

    @Insert
    fun insertAll(vararg news: NewsArticle)
}
