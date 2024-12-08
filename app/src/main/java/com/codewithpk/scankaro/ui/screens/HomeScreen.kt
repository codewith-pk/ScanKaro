package com.codewithpk.scankaro.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codewithpk.scankaro.ui.theme.ScanKaroTheme
import com.codewithpk.scankaro.viewmodel.ToolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, toolViewModel: ToolViewModel) {
    ScanKaroTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "ScanKaro", style = MaterialTheme.typography.titleLarge) }
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
                ScanButton { navController.navigate("scan_screen") }
                Spacer(modifier = Modifier.height(16.dp))
                SecurityScoreView(toolViewModel.securityScore)
                Spacer(modifier = Modifier.height(16.dp))
                ToolsCategoryButton(navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
                ToolCategories()
            }
        }
    }
}

@Composable
fun ScanButton(onScanClicked: () -> Unit) {
    Button(
        onClick = { onScanClicked() },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "Scan your device", color = Color.White)
    }
}

@Composable
fun ToolsCategoryButton(navController: NavController) {
    Button(
        onClick = { navController.navigate("tools_category_screen") },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text = "View Tool Categories", color = Color.White)
    }
}

@Composable
fun SecurityScoreView(securityScore: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(text = "Security Score", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        CircularProgressIndicator(
            progress = securityScore / 100f,
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$securityScore%",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 24.sp
        )
    }
}

@Composable
fun ToolCategories() {
    val categories = listOf(
        "Authentication & Authorization",
        "Data Security",
        "Device Security",
        "Application Security",
        "User Data Privacy",
        "Secure Networking",
        "Fraud Prevention",
        "Compliance and Standards",
        "Development Practices",
        "User Awareness",
        "Wi-Fi Security",
        "Bluetooth Security",
        "SIM Security"
    )

    val icons = listOf(
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock,
        Icons.Default.Lock
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = "Security Tools",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        items(categories.size) { index ->
            ToolCategoryItem(category = categories[index], icon = icons[index])
        }
    }
}

@Composable
fun ToolCategoryItem(category: String, icon: ImageVector) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable { expanded = !expanded }
            .padding(16.dp)
            .animateContentSize()
    ) {
        Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = category,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Details about $category",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewHomeScreen() {
    val toolViewModel = ToolViewModel()
    val navController = rememberNavController()
    HomeScreen(navController, toolViewModel) }
