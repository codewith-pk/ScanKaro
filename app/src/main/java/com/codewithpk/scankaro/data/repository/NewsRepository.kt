package com.codewithpk.scankaro.data.repository

import android.util.Log
import com.codewithpk.scankaro.data.model.NewsArticle
import com.codewithpk.scankaro.data.source.local.NewsDao
import com.codewithpk.scankaro.data.source.remote.NewsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class NewsRepository(private val newsDao: NewsDao, private val newsApiService: NewsApiService) {
    suspend fun getAllNews(): List<NewsArticle> = withContext(Dispatchers.IO) {
        newsDao.getAllNews()
    }

    suspend fun fetchRemoteNews(apiKey: String) = withContext(Dispatchers.IO) {
        try {
            val newsResponse = newsApiService.getTopHeadlines("technology", "en", apiKey)
            Log.d("NewsRepository", "API Response: ${newsResponse.articles}")
            val newsArticles = newsResponse.articles.map { article ->
                article.copy(id = UUID.randomUUID().toString())
            }
            newsDao.insertAll(*newsArticles.toTypedArray())
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error fetching news: ${e.message}")
        }
    }
}
