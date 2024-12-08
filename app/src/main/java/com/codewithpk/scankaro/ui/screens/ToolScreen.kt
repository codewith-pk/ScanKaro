package com.codewithpk.scankaro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.google.accompanist.flowlayout.FlowRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.codewithpk.scankaro.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolScreen(navController: NavController, category: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$category Tools", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        val tools = listOf(
            Tool("Biometric Authentication", R.drawable.ic_authentication),
            // Add more tools here
        )
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            mainAxisSpacing = 8.dp,
            crossAxisSpacing = 8.dp
        ) {
            tools.forEach { tool ->
                ToolItem(navController, tool)
            }
        }
    }
}

data class Tool(val name: String, val icon: Int)

@Composable
fun ToolItem(navController: NavController, tool: Tool) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("tool_detail_screen/${tool.name}") },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = tool.icon),
                contentDescription = null,
                modifier = Modifier.size(64.dp).padding(bottom = 8.dp)
            )
            Text(
                text = tool.name,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
