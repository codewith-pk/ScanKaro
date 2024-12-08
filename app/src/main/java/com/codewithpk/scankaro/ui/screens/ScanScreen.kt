package com.codewithpk.scankaro.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codewithpk.scankaro.ui.theme.ScanKaroTheme
import com.codewithpk.scankaro.viewmodel.ScanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(scanViewModel: ScanViewModel = viewModel(), onBackClicked: () -> Unit) {
    ScanKaroTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Scan Device", style = MaterialTheme.typography.titleLarge) },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ScanProgress(scanViewModel)
                Spacer(modifier = Modifier.height(16.dp))
                ScanResults(scanViewModel)
            }
        }
    }
}

@Composable
fun ScanProgress(scanViewModel: ScanViewModel) {
    val scanProgress by scanViewModel.scanProgress.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = "Scanning...", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        CircularProgressIndicator(
            progress = scanProgress / 100f,
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${scanProgress.toInt()}%",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp
        )
    }
}

@Composable
fun ScanResults(scanViewModel: ScanViewModel) {
    val scanResults by scanViewModel.scanResults.collectAsState()

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(scanResults) { result ->
            ScanResultItem(result)
        }
    }
}

@Composable
fun ScanResultItem(result: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Text(
            text = result,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScanScreen() {
    val scanViewModel = ScanViewModel()
    ScanScreen(scanViewModel, onBackClicked = {})
}
