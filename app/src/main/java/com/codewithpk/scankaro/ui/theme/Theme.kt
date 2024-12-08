package com.codewithpk.scankaro.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.codewithpk.scankaro.ui.screens.HomeScreen

// Light theme color palette
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF9E8DF5),      // PurplePrimary
    secondary = Color(0xFFB0D4FF),    // BlueAccent
    tertiary = Color(0xFFFFD28C),     // OrangeAccent
    background = Color(0xFFF5F5F5),   // LightGrayBackground
    surface = Color(0xFFFFFFFF),      // WhiteBackground
    onPrimary = Color.White,
    onSecondary = Color(0xFF333333),  // DarkText
    onTertiary = Color(0xFF666666),   // LightText
    onBackground = Color(0xFF666666), // LightText
    onSurface = Color(0xFF333333)     // DarkText
)

// Dark theme color palette
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6247AA),      // Darker Purple for Dark Mode
    secondary = Color(0xFF1A73E8),    // BlueAccent for contrast
    tertiary = Color(0xFFFFAB91),     // Muted OrangeAccent
    background = Color(0xFF121212),   // Dark Background
    surface = Color(0xFF1E1E1E),      // Dark Surface
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color(0xFFF5F5F5),   // LightText for readability
    onBackground = Color(0xFFF5F5F5), // LightText for Dark Mode
    onSurface = Color(0xFFF5F5F5)     // LightText
)

@Composable
fun ScanKaroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Use dynamic colors if available (Android 12+)
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,  // Ensure this matches your app's typography
        content = content
    )
}
