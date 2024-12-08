package com.codewithpk.scankaro.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codewithpk.scankaro.data.model.NewsArticle
import com.codewithpk.scankaro.data.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _newsArticles = mutableStateOf<List<NewsArticle>>(emptyList())
    val newsArticles: State<List<NewsArticle>> get() = _newsArticles

    init {
        fetchNews("0f1062b5aec94dc796b183399186cf1a")
    }

    fun fetchNews(apiKey: String) {
        viewModelScope.launch {
            try {
                _newsArticles.value = newsRepository.getAllNews()
                newsRepository.fetchRemoteNews(apiKey)
                _newsArticles.value = newsRepository.getAllNews()
            } catch (e: HttpException) {
                if (e.code() == 401) {
                    // Handle unauthorized error
                    _newsArticles.value = emptyList()
                    // Show error message to the user
                } else {
                    // Handle other errors
                }
            } catch (e: Exception) {
                // Handle other exceptions
            }
        }
    }
}
