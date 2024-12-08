package com.codewithpk.scankaro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.codewithpk.scankaro.ui.screens.*
import com.codewithpk.scankaro.viewmodel.ToolViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    private val toolViewModel = ToolViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            MainScreen(navController = navController, toolViewModel = toolViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, toolViewModel: ToolViewModel) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) {
        SetupNavGraph(navController = navController, toolViewModel = toolViewModel, padding = it)
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController, toolViewModel: ToolViewModel, padding: PaddingValues) {
    NavHost(navController = navController, startDestination = BottomNavItem.Home.route) {
        composable(BottomNavItem.Home.route) {
            HomeScreen(navController = navController, toolViewModel = toolViewModel)
        }
        composable(BottomNavItem.Tools.route) {
            ToolsCategoryScreen(navController = navController, onBackClicked = { navController.navigateUp() })
        }
        composable(BottomNavItem.Scan.route) {
            ScanScreen(onBackClicked = { navController.navigateUp() })
        }
        composable(BottomNavItem.News.route) {
            NewsScreen(navController = navController)
        }
        composable(BottomNavItem.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable("news_detail_screen/{newsId}") { backStackEntry ->
            NewsDetailScreen(navController, backStackEntry)
        }
        composable("tool_screen/{category}") { backStackEntry ->
            ToolScreen(navController = navController, category = backStackEntry.arguments?.getString("category") ?: "")
        }
        composable("tool_detail_screen/{toolName}") { backStackEntry ->
            ToolDetailScreen(navController = navController, toolName = backStackEntry.arguments?.getString("toolName") ?: "")
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Tools,
        BottomNavItem.Scan,
        BottomNavItem.News,
        BottomNavItem.Profile
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, "home_screen")
    object Tools : BottomNavItem("Tools", Icons.Default.List, "tools_category_screen")
    object Scan : BottomNavItem("Scan", Icons.Default.Search, "scan_screen")
    object News : BottomNavItem("News", Icons.Default.Notifications, "news_screen")
    object Profile : BottomNavItem("Profile", Icons.Default.Person, "profile_screen")
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    MainScreen(navController = rememberNavController(), toolViewModel = ToolViewModel())
}
