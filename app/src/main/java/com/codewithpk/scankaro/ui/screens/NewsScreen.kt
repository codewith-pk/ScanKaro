package com.codewithpk.scankaro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.codewithpk.scankaro.data.model.NewsArticle
import com.codewithpk.scankaro.data.repository.NewsRepository
import com.codewithpk.scankaro.network.NetworkModule
import com.codewithpk.scankaro.ui.theme.ScanKaroTheme
import com.codewithpk.scankaro.viewmodel.NewsViewModel
import com.codewithpk.scankaro.viewmodel.NewsViewModelFactory
import com.codewithpk.scankaro.data.source.local.AppDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController) {
    val context = navController.context
    val database = AppDatabase.getDatabase(context)
    val newsDao = database.newsDao()
    val apiKey = "YOUR_API_KEY"
    val newsApiService = NetworkModule.provideNewsApiService(apiKey)
    val newsRepository = NewsRepository(newsDao, newsApiService)
    val factory = NewsViewModelFactory(newsRepository)
    val viewModel: NewsViewModel = viewModel(factory = factory)

    ScanKaroTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Security News", style = MaterialTheme.typography.titleLarge) }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                SecurityNewsList(navController, viewModel.newsArticles.value)
                LaunchedEffect(Unit) {
                    viewModel.fetchNews(apiKey)
                }
            }
        }
    }
}

@Composable
fun SecurityNewsList(navController: NavController, newsArticles: List<NewsArticle>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(newsArticles) { news ->
            NewsItem(navController, news)
        }
    }
}

@Composable
fun NewsItem(navController: NavController, news: NewsArticle) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("news_detail_screen/${news.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            news.urlToImage?.let {
                Image(
                    painter = rememberImagePainter(data = it),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.title,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = news.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewsScreen() {
    NewsScreen(navController = rememberNavController())
}
