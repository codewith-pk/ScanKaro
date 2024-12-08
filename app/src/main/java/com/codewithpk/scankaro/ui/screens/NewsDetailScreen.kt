package com.codewithpk.scankaro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.codewithpk.scankaro.data.model.NewsArticle
import com.codewithpk.scankaro.data.repository.NewsRepository
import com.codewithpk.scankaro.ui.theme.ScanKaroTheme
import com.codewithpk.scankaro.viewmodel.NewsViewModel
import com.codewithpk.scankaro.viewmodel.NewsViewModelFactory
import com.codewithpk.scankaro.data.source.local.AppDatabase
import com.codewithpk.scankaro.network.NetworkModule

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(navController: NavController, backStackEntry: NavBackStackEntry) {
    val newsId = backStackEntry.arguments?.getString("newsId")
    val context = navController.context
    val database = AppDatabase.getDatabase(context)
    val newsDao = database.newsDao()
    val newsApiService = NetworkModule.provideNewsApiService("0f1062b5aec94dc796b183399186cf1a")
    val newsRepository = NewsRepository(newsDao, newsApiService)
    val factory = NewsViewModelFactory(newsRepository)
    val viewModel: NewsViewModel = viewModel(factory = factory)

    val newsArticle = viewModel.newsArticles.value.find { it.id == newsId }

    ScanKaroTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "News Detail", style = MaterialTheme.typography.titleLarge) },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            newsArticle?.let { article ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    article.urlToImage?.let {
                        Image(
                            painter = rememberImagePainter(data = it),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article.content ?: "Content not available",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewNewsDetailScreen() {
//    val navController = rememberNavController()
//    NewsDetailScreen(navController, NavBackStackEntry.createDummy())
//}
