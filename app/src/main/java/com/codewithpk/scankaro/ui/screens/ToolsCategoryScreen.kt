package com.codewithpk.scankaro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.codewithpk.scankaro.R
import com.codewithpk.scankaro.ui.theme.ScanKaroTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolsCategoryScreen(navController: NavController, onBackClicked: () -> Unit) {
    ScanKaroTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Tool Categories", style = MaterialTheme.typography.titleLarge) },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            ToolCategories(navController, padding)
        }
    }
}

@Composable
fun ToolCategories(navController: NavController, padding: PaddingValues) {
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
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication,
        R.drawable.ic_authentication
    )

    LazyColumn(
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(categories) { index, category ->
            ToolCategoryItem(category = category, icon = icons.getOrNull(index)) {
                navController.navigate("tool_screen/${category}")
            }
        }
    }
}

@Composable
fun ToolCategoryItem(category: String, icon: Int?, onItemClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            icon?.let {
                Image(painter = painterResource(id = it), contentDescription = null, modifier = Modifier.size(44.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = category,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewToolsCategoryScreen() {
    val navController = rememberNavController()
    ToolsCategoryScreen(navController = navController, onBackClicked = {})
}
