package com.codewithpk.scankaro.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "news")
data class NewsArticle(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String?,
    val content: String?,
    val publishedAt: String,
    val url: String,
    val urlToImage: String?
)
